package com.josequal;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.josequal.model.DrawerItem;

public class ItemDetailsDialogFragment extends DialogFragment {
    private DrawerItem item;

    public ItemDetailsDialogFragment(DrawerItem item) {
        this.item = item;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog_layout, null);

        ImageView dialogImageView = dialogView.findViewById(R.id.dialogImageView);
        TextView dialogDetailsTextView = dialogView.findViewById(R.id.dialogDetailsTextView);

        dialogImageView.setImageResource(item.getImageResourceId());
        dialogDetailsTextView.setText(item.getDetails());

        builder.setView(dialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle OK button click if needed
                    }
                });

        return builder.create();
    }
}