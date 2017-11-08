package com.matapp.matapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.matapp.matapp.R;

/**
 * Created by kathrinkoebel on 05.11.17.
 */

public class MatDeleteAlertDialogFragment extends DialogFragment {

    /* Listener Interface with method for passing back data result  */
    public interface MatDeleteDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, int id);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    MatDeleteDialogListener matDeleteDialogListener;

    // Empty constructor required for DialogFragment
    public MatDeleteAlertDialogFragment(){
    }

    public static MatDeleteAlertDialogFragment newInstance(int id) {
        MatDeleteAlertDialogFragment frag = new MatDeleteAlertDialogFragment();
        Bundle args = new Bundle();
        args.putInt("ID", id);
        frag.setArguments(args);
        return frag;
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            matDeleteDialogListener = (MatDeleteDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement MatDeleteDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        /*
        this is for custom Layout of Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.xxx, null));
        */

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.delete_item_title)
                .setMessage(R.string.delete_item_text)
                .setPositiveButton(R.string.btn_delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Delete Item & load MatList
                        // needs some reference to uniqueID or position of item...
                        int mat_id = getArguments().getInt("ID");
                        matDeleteDialogListener.onDialogPositiveClick(MatDeleteAlertDialogFragment.this, mat_id);
                        //dismiss();
                    }
                })
                .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss();
                    }
                });
        return builder.create();
    }
}