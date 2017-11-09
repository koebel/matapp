package com.matapp.matapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.matapp.matapp.MainActivity;
import com.matapp.matapp.R;


/**
 *
 * This dialog fragment displays a mini dialog onDelete of an item
 * to let the user confirm that a Material should get deleted for good.
 *
 * Created by kathrinkoebel on 05.11.17.
 *
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

    // Override the Fragment.onAttach() method to instantiate the MatDeleteDialogListener
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

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.delete_item_title)
                .setMessage(R.string.delete_item_text)
                .setPositiveButton(R.string.btn_delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // TODO Delete Item
                        // this will provide reference to uniqueID of the item...
                        int mat_id = getArguments().getInt("ID");

                        matDeleteDialogListener.onDialogPositiveClick(MatDeleteAlertDialogFragment.this, mat_id);
                    }
                })
                .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        return builder.create();
    }
}