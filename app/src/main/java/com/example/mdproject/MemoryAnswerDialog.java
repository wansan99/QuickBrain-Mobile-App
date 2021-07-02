package com.example.mdproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class MemoryAnswerDialog extends AppCompatDialogFragment {
    private EditText editTextAnswer;
    private MemoryAnswerDialogListener listener;
    private int actualAnswer;

    public static MemoryAnswerDialog newInstance(int passedAnswer) {
        MemoryAnswerDialog dialog = new MemoryAnswerDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("CELL_ANSWER", passedAnswer);
        dialog.setArguments(bundle);
        return dialog;
    }

    public Dialog onCreateDialog (Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        actualAnswer = getArguments().getInt("CELL_ANSWER");

        builder.setView(view)
                .setTitle("Answer")
                .setNegativeButton("Return", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String answer = editTextAnswer.getText().toString();
                        answer = answer.replaceAll("[^\\d.]", "");
                        listener.checkAnswer(answer, actualAnswer);
                    }
                });
        editTextAnswer = view.findViewById(R.id.editTextAnswer);


        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (MemoryAnswerDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException((context.toString() + "must implement MemoryAnswerDialogListener"));
        }
    }

    public interface MemoryAnswerDialogListener {
        void checkAnswer(String answer, int cellAnswer);
    }
}
