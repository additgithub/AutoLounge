package com.aprilinnovations.autoloungeindia.async;

import android.content.Intent;
import android.os.AsyncTask;

import com.aprilinnovations.autoloungeindia.R;
import com.aprilinnovations.autoloungeindia.async.bean.PickResult;
import com.aprilinnovations.autoloungeindia.async.bundle.PickSetup;
import com.aprilinnovations.autoloungeindia.async.enums.EPickType;
import com.aprilinnovations.autoloungeindia.async.img.ImageHandler;
import com.aprilinnovations.autoloungeindia.async.resolver.IntentResolver;

import java.lang.ref.WeakReference;


public class AsyncImageResult extends AsyncTask<Intent, Void, PickResult> {

    private WeakReference<IntentResolver> weakIntentResolver;
    private WeakReference<PickSetup> weakSetup;
    private OnFinish onFinish;

    public AsyncImageResult(IntentResolver intentResolver, PickSetup setup) {
        this.weakIntentResolver = new WeakReference<>(intentResolver);
        this.weakSetup = new WeakReference<>(setup);
    }


    public AsyncImageResult setOnFinish(OnFinish onFinish) {
        this.onFinish = onFinish;
        return this;
    }

    @Override
    protected PickResult doInBackground(Intent... intents) {

        //Create a PickResult instance
        PickResult result = new PickResult();

        IntentResolver resolver = weakIntentResolver.get();

        if (resolver == null) {
            result.setError(new Error(resolver.getActivity().getString(R.string.activity_destroyed)));
            return result;
        }

        try {
            //Get the data intent from onActivityResult()
            Intent data = intents[0];

            //Define if it was pick from camera_photo
            boolean fromCamera = resolver.fromCamera(data);

            //Instance of a helper class
            ImageHandler imageHandler = ImageHandler
                    .with(resolver.getActivity()).setup(weakSetup.get())
                    .provider(fromCamera ? EPickType.CAMERA : EPickType.GALLERY)
                    .uri(fromCamera ? resolver.cameraUri() : data.getData());

            //Setting uri and path for result
            result.setUri(imageHandler.getUri())
                    .setPath(imageHandler.getUriPath())
                    .setBitmap(imageHandler.decode());

            if(fromCamera)
                result.setPickType(EPickType.CAMERA);
            else
                result.setPickType(EPickType.GALLERY);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(e);
            return result;
        }
    }


    @Override
    protected void onPostExecute(PickResult r) {
        if (onFinish != null)
            onFinish.onFinish(r);
    }

    public interface OnFinish {
        void onFinish(PickResult pickResult);
    }

}
