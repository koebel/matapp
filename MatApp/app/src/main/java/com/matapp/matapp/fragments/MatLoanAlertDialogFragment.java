package com.matapp.matapp.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

/**
 * Created by kathrinkoebel on 08.11.17.
 */




public class MatLoanAlertDialogFragment extends DialogFragment {
    private EditText loan_dialog_name, loan_dialog_contact, loan_dialog_until, loan_dialog_note;
    private String title;
    private int uniqueId;

    /* Listener Interface with method for passing back data result */
    public interface LoanDialogListener {
        public void onFinishLoanDialog(String loanName, String loanContact, String loanUntil, String loanNote);
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
    // ...
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // ...
        // 2. Setup a callback when the "Done" button is pressed on keyboard
        mEditText.setOnEditorActionListener(this);
    }
    */

    /*
    // Fires whenever the textfield has an action performed
    // In this case, when the "Done" button is pressed
    // REQUIRES a 'soft keyboard' (virtual keyboard)
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text back to activity through the implemented listener
            EditNameDialogListener listener = (EditNameDialogListener) getActivity();
            listener.onFinishEditDialog(mEditText.getText().toString());
            // Close the dialog and return back to the parent activity
            dismiss();
            return true;
        }
        return false;
    }
    */
}