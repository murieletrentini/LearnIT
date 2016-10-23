package ch.hsr.mge.learnit.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class YesNoDialog extends DialogFragment {

    public interface DialogListener {
        void onFinishDialog(int resultCode);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        String message = args.getString("MESSAGE", "Continue?");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DialogListener activity = (DialogListener) getActivity();
                        activity.onFinishDialog(Activity.RESULT_OK);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DialogListener activity = (DialogListener) getActivity();
                        activity.onFinishDialog(Activity.RESULT_CANCELED);
                    }
                });
        return builder.create();
    }
}