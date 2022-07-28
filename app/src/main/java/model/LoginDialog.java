package model;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mynotes.R;

public class LoginDialog extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View loginDialog = inflater.inflate(R.layout.login_dialog, null);
        loginDialog.findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = loginDialog.<EditText>findViewById(R.id.login_input).getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("USER_NAME", userName );
                requireActivity().getSupportFragmentManager().setFragmentResult("LOGIN_RESULT", bundle);
                dismiss();
            }
        });

        return loginDialog;
    }
}
