package com.matapp.matapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.matapp.matapp.R;

/**
 * Created by kathrinkoebel on 08.11.17.
 */


public class MatLoanAlertDialogFragment extends DialogFragment {
    private EditText loan_dialog_name, loan_dialog_contact, loan_dialog_until, loan_dialog_note;
    private String title;
    private int uniqueId;

    /* Listener Interface with method for passing back data result */
    public interface LoanDialogListener {
        public void onFinishLoanDialog(DialogFragment dialog, String loanName, String loanContact, String loanUntil, String loanNote);
        //public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    MatLoanAlertDialogFragment.LoanDialogListener loanDialogListener;

    // Empty constructor required for DialogFragment
    public MatLoanAlertDialogFragment(){
    }

    public static MatLoanAlertDialogFragment newInstance(String title) {
        MatLoanAlertDialogFragment frag = new MatLoanAlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("TITLE", title);
        frag.setArguments(args);
        return frag;
    }

    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_mat_loan, container);
    }
    */

    /*
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get fields from view
        loan_dialog_name = (EditText) getActivity().findViewById(R.id.loan_dialog_name);
        loan_dialog_contact = (EditText) getActivity().findViewById(R.id.loan_dialog_contact);
        loan_dialog_until = (EditText) getActivity().findViewById(R.id.loan_dialog_until);
        loan_dialog_note = (EditText) getActivity().findViewById(R.id.loan_dialog_note);

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        loan_dialog_name.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
    */

    // Override the Fragment.onAttach() method to instantiate the LoanDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            loanDialogListener = (MatLoanAlertDialogFragment.LoanDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement MatLoanAlertDialogFragment");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_mat_loan, null));
        builder.setTitle(R.string.loan_dialog_title)

        //loan_dialog_contact.getText();
                /*
                .setPositiveButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Delete Item & load MatList
                        //loan_dialog_name = (EditText) view.findViewById(R.id.loan_dialog_name);

                        //loan_dialog_name
                        // loan_dialog_contact, loan_dialog_until, loan_dialog_note;

                        // needs some reference to uniqueID or position of item...
                        int mat_id = getArguments().getInt("ID");
                        loanDialogListener.onFinishLoanDialog(MatLoanAlertDialogFragment.this, "Name", "Contact", "Date", "Note");
                        //dismiss();
                    }
                })
                */
                .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

        return builder.create();
    }
}