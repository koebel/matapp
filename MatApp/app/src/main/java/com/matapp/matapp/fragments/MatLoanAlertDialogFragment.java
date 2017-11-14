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
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.matapp.matapp.R;

/**
 *
 * This dialog fragment displays an alert dialog when a user requests to loan an item.
 *
 * Created by kathrinkoebel on 08.11.17.
 *
 */


public class MatLoanAlertDialogFragment extends DialogFragment {

    /* Variables */
    private String title;
    private String loanName;
    private String loanContact;
    private String loanUntil;
    private String loanNote;

    /* static Variables */
    public static final String LOG_ALERT_DIALOG = "AlertDialog";

    /* Listener Interface with method for passing back data result */
    public interface LoanDialogListener {
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
        // uniqueId = getArguments().getInt("ID", 0);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_mat_loan, null));

        LinearLayout layout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(paddingDpBig, paddingDpBig, paddingDpBig, paddingDpSmall);
        layout.setLayoutParams(layoutParams);
        layout.setOrientation(LinearLayout.VERTICAL);

        // create Input Fields & labels
        final TextView labelName = new TextView(getActivity());
        labelName.setText(R.string.det_loan_name_label);
        labelName.setPadding(paddingDpBig, paddingDpBig, paddingDpBig, 0);
        final EditText loanDialogName = new EditText(getActivity());
        loanDialogName.setHint(R.string.det_loan_name_hint);
        loanDialogName.setPadding(paddingDpBig, paddingDpSmall, paddingDpBig, paddingDpSmall);

        final TextView labelContact = new TextView(getActivity());
        labelContact.setText(R.string.det_loan_contact_label);
        labelContact.setPadding(paddingDpBig, paddingDpSmall, paddingDpBig, 0);
        final EditText loanDialogContact = new EditText(getActivity());
        loanDialogContact.setHint(R.string.det_loan_contact_hint);
        loanDialogContact.setPadding(paddingDpBig, paddingDpSmall, paddingDpBig, paddingDpSmall);

        final TextView labelUntil = new TextView(getActivity());
        labelUntil.setText(R.string.det_loan_until_label);
        labelUntil.setPadding(paddingDpBig, paddingDpSmall, paddingDpBig, 0);
        final EditText loanDialogUntil = new EditText(getActivity());
        loanDialogUntil.setHint(R.string.det_loan_until_hint);
        loanDialogUntil.setPadding(paddingDpBig, paddingDpSmall, paddingDpBig, paddingDpSmall);

        final TextView labelNote = new TextView(getActivity());
        labelNote.setText(R.string.det_loan_note_label);
        labelNote.setPadding(paddingDpBig, paddingDpSmall, paddingDpBig, 0);
        final EditText loanDialogNote = new EditText(getActivity());
        loanDialogNote.setHint(R.string.det_loan_note_hint);
        loanDialogNote.setPadding(paddingDpBig, paddingDpSmall, paddingDpBig, paddingDpSmall);

        // add them to layout
        layout.addView(labelName);
        layout.addView(loanDialogName);
        layout.addView(labelContact);
        layout.addView(loanDialogContact);
        layout.addView(labelUntil);
        layout.addView(loanDialogUntil);
        layout.addView(labelNote);
        layout.addView(loanDialogNote);

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

                        Log.i(LOG_ALERT_DIALOG,"Name: "+loanDialogName.getText().toString());
                        Log.i(LOG_ALERT_DIALOG,"Contact: "+loanDialogContact.getText().toString());
                        Log.i(LOG_ALERT_DIALOG,"Until: "+loanDialogUntil.getText().toString());
                        Log.i(LOG_ALERT_DIALOG,"Note: "+loanDialogNote.getText().toString());

                        // Send the positive button event back to the host activity
                        loanName = loanDialogName.getText().toString();

                        // check if Name has been entered
                            if (TextUtils.isEmpty(loanName)) {
                                Toast.makeText(getContext(), R.string.det_loan_name_error, Toast.LENGTH_SHORT).show();
                            }
                        loanContact = loanDialogContact.getText().toString();
                        loanUntil = loanDialogUntil.getText().toString();
                        loanNote = loanDialogNote.getText().toString();

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