package com.matapp.matapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public static MatLoanAlertDialogFragment newInstance(String title) { // removed: int id
        MatLoanAlertDialogFragment frag = new MatLoanAlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("TITLE", title);
        //args.putInt("ID", id);
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

        // convert px to dp
        float paddingPxBig = 24f;
        int paddingDpBig = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, paddingPxBig, getActivity().getResources().getDisplayMetrics());

        float paddingPxSmall = 12f;
        int paddingDpSmall = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, paddingPxSmall, getActivity().getResources().getDisplayMetrics());

        // Fetch arguments from bundle and set title
        title = getArguments().getString("TITLE", "Material Title");
        uniqueId = getArguments().getInt("ID", 0);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_mat_loan, null));

        LinearLayout layout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(paddingDpBig, paddingDpBig, paddingDpBig, paddingDpSmall);
        layout.setLayoutParams(layoutParams);
        layout.setOrientation(LinearLayout.VERTICAL);

        // create Input Fields & labels
        final TextView label_name = new TextView(getActivity());
        label_name.setText(R.string.det_loan_name_label);
        label_name.setPadding(paddingDpBig, paddingDpBig, paddingDpBig, 0);
        final EditText loan_dialog_name = new EditText(getActivity());
        loan_dialog_name.setHint(R.string.det_loan_name_hint);
        loan_dialog_name.setPadding(paddingDpBig, paddingDpSmall, paddingDpBig, paddingDpSmall);

        final TextView label_contact = new TextView(getActivity());
        label_contact.setText(R.string.det_loan_contact_label);
        label_contact.setPadding(paddingDpBig, paddingDpSmall, paddingDpBig, 0);
        final EditText loan_dialog_contact = new EditText(getActivity());
        loan_dialog_contact.setHint(R.string.det_loan_contact_hint);
        loan_dialog_contact.setPadding(paddingDpBig, paddingDpSmall, paddingDpBig, paddingDpSmall);

        final TextView label_until = new TextView(getActivity());
        label_until.setText(R.string.det_loan_until_label);
        label_until.setPadding(paddingDpBig, paddingDpSmall, paddingDpBig, 0);
        final EditText loan_dialog_until = new EditText(getActivity());
        loan_dialog_until.setHint(R.string.det_loan_until_hint);
        loan_dialog_until.setPadding(paddingDpBig, paddingDpSmall, paddingDpBig, paddingDpSmall);

        final TextView label_note = new TextView(getActivity());
        label_note.setText(R.string.det_loan_note_label);
        label_note.setPadding(paddingDpBig, paddingDpSmall, paddingDpBig, 0);
        final EditText loan_dialog_note = new EditText(getActivity());
        loan_dialog_note.setHint(R.string.det_loan_note_hint);
        loan_dialog_note.setPadding(paddingDpBig, paddingDpSmall, paddingDpBig, paddingDpSmall);

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
        String loan = getText(R.string.loan_txt).toString();
        builder.setTitle(title+ " " + loan)

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
}