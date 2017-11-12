package com.matapp.matapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.matapp.matapp.R;

/**
 * Created by kathrinkoebel on 08.11.17.
 */


public class MatLoanAlertDialogFragment extends DialogFragment {
    private EditText loan_dialog_name, loan_dialog_contact, loan_dialog_until, loan_dialog_note;
    private String title, loanName, loanContact, loanUntil, loanNote;
    private int uniqueId;

    /* Listener Interface with method for passing back data result */
    public interface LoanDialogListener {
        //public void onFinishLoanDialog(String loanName, String loanContact, String loanUntil, String loanNote);
        public void onLoanDialogPositiveClick(DialogFragment dialog, String loanName, String loanContact, String loanUntil, String loanNote);
        public void onLoanDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    LoanDialogListener loanDialogListener;

    // Empty constructor required for DialogFragment
    public MatLoanAlertDialogFragment(){
    }

    public static MatLoanAlertDialogFragment newInstance(String title, int id) {
        MatLoanAlertDialogFragment frag = new MatLoanAlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("TITLE", title);
        args.putInt("ID", id);
        frag.setArguments(args);
        return frag;
    }

    // Override the Fragment.onAttach() method to instantiate the LoanDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            loanDialogListener = (LoanDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement MatLoanAlertDialogFragment");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Fetch arguments from bundle and set title
        title = getArguments().getString("TITLE", "Material Title");
        uniqueId = getArguments().getInt("ID", 0);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_mat_loan, null));

        LinearLayout layout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(16, 0, 16, 0);
        layout.setLayoutParams(layoutParams);
        layout.setOrientation(LinearLayout.VERTICAL);

        // TODO: fix Layout

        // create Input Fields & labels
        final TextView label_name = new TextView(getActivity());
        label_name.setText(R.string.det_loan_name_label);
        final EditText loan_dialog_name = new EditText(getActivity());
        loan_dialog_name.setHint(R.string.det_loan_name_hint);

        final TextView label_contact = new TextView(getActivity());
        label_contact.setText(R.string.det_loan_contact_label);
        final EditText loan_dialog_contact = new EditText(getActivity());
        loan_dialog_contact.setHint(R.string.det_loan_contact_hint);

        final TextView label_until = new TextView(getActivity());
        label_until.setText(R.string.det_loan_until_label);
        final EditText loan_dialog_until = new EditText(getActivity());
        loan_dialog_until.setHint(R.string.det_loan_until_hint);

        final TextView label_note = new TextView(getActivity());
        label_note.setText(R.string.det_loan_note_label);
        final EditText loan_dialog_note = new EditText(getActivity());
        loan_dialog_note.setHint(R.string.det_loan_note_hint);


        // add them to layout
        layout.addView(label_name);
        layout.addView(loan_dialog_name);
        layout.addView(label_contact);
        layout.addView(loan_dialog_contact);
        layout.addView(label_until);
        layout.addView(loan_dialog_until);
        layout.addView(label_note);
        layout.addView(loan_dialog_note);

        builder.setView(layout);

        /*
        View inputView = inflater.inflate(R.layout.dialog_mat_loan, null);

        final EditText loan_dialog_name = (EditText) inputView.findViewById(R.id.loan_dialog_name);
        final EditText loan_dialog_contact = (EditText) inputView.findViewById(R.id.loan_dialog_contact);
        final EditText loan_dialog_until = (EditText) inputView.findViewById(R.id.loan_dialog_until);
        final EditText loan_dialog_note = (EditText) inputView.findViewById(R.id.loan_dialog_note);
        */

        //builder.setTitle(R.string.loan_dialog_title)
        builder.setTitle(title)
        //builder.setTitle(title + " ausleihen")
                // Add action buttons
                .setPositiveButton(R.string.btn_loan, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Log.i("AlertDialog","Name: "+loan_dialog_name.getText().toString());
                        Log.i("AlertDialog","Contact: "+loan_dialog_contact.getText().toString());
                        Log.i("AlertDialog","Until: "+loan_dialog_until.getText().toString());
                        Log.i("AlertDialog","Note: "+loan_dialog_note.getText().toString());

                        // Send the positive button event back to the host activity
                        loanName = loan_dialog_name.getText().toString();

                        // check if Name has been entered
                            if (TextUtils.isEmpty(loanName)) {
                                Toast.makeText(getContext(), R.string.det_loan_name_error, Toast.LENGTH_SHORT).show();
                            }
                        loanContact = loan_dialog_contact.getText().toString();
                        loanUntil = loan_dialog_until.getText().toString();
                        loanNote = loan_dialog_note.getText().toString();

                        loanDialogListener.onLoanDialogPositiveClick(MatLoanAlertDialogFragment.this, loanName, loanContact, loanUntil, loanNote);
                    }
                })
                .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the positive button event back to the host activity
                        loanDialogListener.onLoanDialogNegativeClick(MatLoanAlertDialogFragment.this);
                    }
                });
        return builder.create();
    }




    /*

    // Fires whenever the textfield has an action performed
    // In this case, when the "Done" button is pressed
    // REQUIRES a 'soft keyboard' (virtual keyboard)
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text back to activity through the implemented listener
            LoanDialogListener listener = (LoanDialogListener) getActivity();
            listener.onFinishLoanDialog(loan_dialog_name.getText().toString(), "Contact", "Until", "Note");
            // Close the dialog and return back to the parent activity
            dismiss();
            return true;
        }
        return false;
    }

*/

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


        //loan_dialog_name.setOnEditorActionListener(this);

        // Fetch arguments from bundle and set title
        title = getArguments().getString("TITLE", "Material Title");
        uniqueId = getArguments().getInt("ID", 0);
        //getDialog().setTitle(title + " Ausleihen");
        getDialog().setTitle(title);

        // Show soft keyboard automatically and request focus to field
        loan_dialog_name.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
    */


}