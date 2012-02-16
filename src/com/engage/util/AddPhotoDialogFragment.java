package com.engage.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.engage.R;

public class AddPhotoDialogFragment extends DialogFragment {
	public final static int SELECT_PHOTO = 0;
	public final static int TAKE_PHOTO = 1;

	static AddPhotoDialogFragment newInstance() {
		AddPhotoDialogFragment f = new AddPhotoDialogFragment();
		return f;
	}

	public static void showAddPhotoDialog(FragmentActivity activity) {
		// show welcome dialog
		FragmentTransaction ft = activity.getSupportFragmentManager()
				.beginTransaction();
		Fragment prev = activity.getSupportFragmentManager().findFragmentByTag(
				"add-photo-dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);

		// Create and show the dialog.
		AddPhotoDialogFragment newFragment = AddPhotoDialogFragment
				.newInstance();
		newFragment.show(ft, "welcome-dialog");
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setPositiveButton(R.string.user_choose_photo_device,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						setPhotoFromDevice();
					}
				})
				.setNeutralButton(R.string.user_choose_photo_use_camera,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								setPhotoFromCamera();
							}
						})
				.setNegativeButton(R.string.button_cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).setTitle(R.string.user_choose_photo)
				.setMessage(R.string.user_choose_photo_description);
		return builder.create();
	}

	private void setPhotoFromDevice() {
		Intent getPictureIntent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		startActivityForResult(getPictureIntent, SELECT_PHOTO);
		Toast.makeText(getActivity(), "Selected from gallery",
				Toast.LENGTH_SHORT).show();
	}

	private void setPhotoFromCamera() {
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(cameraIntent, TAKE_PHOTO);
		Toast.makeText(getActivity(), "Take picture from camera",
				Toast.LENGTH_SHORT).show();
	}

}
