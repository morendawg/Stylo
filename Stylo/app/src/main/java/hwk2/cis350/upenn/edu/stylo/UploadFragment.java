package hwk2.cis350.upenn.edu.stylo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Daniel Moreno
 * This class allows a user to upload a picture from their camera or their gallery. They can preview
 * it before posting it to Stylo.
 */

public class UploadFragment extends Fragment {

    public int SELECTED_IMAGE;
    private Uri outputFileUri;
    String imagePath;
    ImageView previewImageView;

    /**
     * This method retrieves the appropriate layout file and sets the methods for the button to
     * select an image.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_upload,container,false);
        Button selectImage = (Button) v.findViewById(R.id.selectImage);
        previewImageView = (ImageView) v.findViewById(R.id.preview);
        selectImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                selectImage(v);
            }
        });
        Button upload = (Button) v.findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                upload(v);
            }
        });
        return v;
    }


    /**
     * This method creates a file on the phones DCIM folder to store the new image that will be taken
     * in case the user selects the camera as the source of the file.
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        imagePath = "file:" + image.getAbsolutePath();
        outputFileUri = Uri.fromFile(image);
        return image;
    }

    public void selectImage(View view) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        /**
         * creates an ArrayList of intents to allow the user to pick between the gallery or the
         * camera as the source of the image
         */

        ArrayList<Intent> intents = new ArrayList<Intent>();
        intents.add(pickIntent);

        /**
         * Ensures that the user can capture an image with the camera. If not, then only the gallery
         * option is displayed
         */
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (captureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            if (photoFile != null) {
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                intents.add(captureIntent);
            }
        }

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");

        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents.toArray(new Parcelable[intents.size()]));

        startActivityForResult(chooserIntent, SELECTED_IMAGE);

    }

    /**
     * This method sets the preview image depending on the source of the image. (either the gallery
     * or the camera)
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECTED_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                previewImageView.setImageURI(outputFileUri);
            } else {
                previewImageView.setImageURI(data.getData());
            }
        }
    }

    public void upload(View v){


        /*
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), outputFileUri);
        } catch (IOException e) {

        }
        ByteArrayOutputStream inputImage = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, inputImage);

        //convert image to byte array and then to a string
        byte[] byteArray = inputImage.toByteArray();
        String imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);
        Post p = new Post(imageFile, new Date(), GlobalData.getInstance().getUserID());
        BackendAPI.getInstance().createPost("-KGUi5SrdxyC9EDDuqgQ", p);
        */
    }
}
