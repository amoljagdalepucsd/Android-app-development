package com.example.showpost;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

public class Account extends AppCompatDialogFragment {

    private TextView textView;
    private dialogListener listener1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle=getArguments();
        String username=bundle.getString("user");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.account, null);

        builder.setView(view)

                .setTitle("Account")
                .setNegativeButton("close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("sign out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener1.add1();
                    }
                });
        textView=view.findViewById(R.id.accountname);
        textView.setText(username);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener1 = (dialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface dialogListener {
        void add1();
    }
}

