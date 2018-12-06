package thanh.ha.ui.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import thanh.ha.R;


public class DialogMessage {
    private AlertDialog alertDialog;
    private TextView tvMessage;
    private OnClickButton onClickButton;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onClickButton != null) {
                switch (view.getId()) {
                    case R.id.btn_cancel_dialog:
                        onClickButton.onClickNegative();
                        break;
                    case R.id.btn_ok_dialog:
                        onClickButton.onClickPositive();
                        break;
                }
            }
            alertDialog.dismiss();
        }
    };

    public DialogMessage(Context context) {
        LayoutInflater _inflater = LayoutInflater.from(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = _inflater.inflate(R.layout.view_yes_no_dialog, null);
        tvMessage = view.findViewById(R.id.tv_content);
        TextView tvActionNegative = view.findViewById(R.id.btn_cancel_dialog);
        TextView tvActionPositive = view.findViewById(R.id.btn_ok_dialog);

        tvActionNegative.setOnClickListener(onClickListener);
        tvActionPositive.setOnClickListener(onClickListener);
        builder.setView(view);
        alertDialog = builder.create();
    }

    public void setOnClickButton(OnClickButton onClickButton) {
        this.onClickButton = onClickButton;
    }

    public void setMessage(String message) {
        tvMessage.setText(message);
    }


    public void showDialog() {
        alertDialog.show();
    }


    public interface OnClickButton {
        void onClickNegative();

        void onClickPositive();
    }

}
