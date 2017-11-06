package com.matapp.matapp.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.matapp.matapp.R;

/**
 * Created by kathrinkoebel on 05.11.17.
 */

public class MatDeleteAlertDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())

                .setTitle(getText(R.string.delete_item_title).toString())
                .setMessage(getText(R.string.delete_item_text).toString())
                .setPositiveButton(getText(R.string.btn_delete).toString(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Delete Item & load MatList
                        dismiss();
                    }
                })
                .setNegativeButton(getText(R.string.btn_cancel).toString(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                }).create();
    }
}