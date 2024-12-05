package com.dirajarasyad.carthub.fragment;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.database.manager.DBUserManager;
import com.dirajarasyad.carthub.manager.ImageManager;
import com.dirajarasyad.carthub.manager.PickerManager;
import com.dirajarasyad.carthub.manager.SessionManager;
import com.dirajarasyad.carthub.model.User;

import java.util.regex.Pattern;

public class ProfileEditFragment extends Fragment {
    private TextView profile_editProfileTV, profile_editUsernameTV, profile_editPasswordTV, profile_editEmailTV, profile_editPhoneTV, profile_editAddressTV, profile_editConfirmTV, profile_editProfileErrorTV, profile_editUsernameErrorTV, profile_editPasswordErrorTV, profile_editEmailErrorTV, profile_editPhoneErrorTV, profile_editAddressErrorTV, profile_editConfirmErrorTV;
    private ImageView profile_editProfileIV;
    private EditText profile_editUsernameET, profile_editPasswordET, profile_editEmailET, profile_editPhoneET, profile_editAddressET, profile_editConfirmET;
    private Button profile_editSaveBtn, profile_editRequestBtn, profile_editAcceptBtn, profile_editCancelBtn, profile_editDeleteBtn;
    private User user, session;
    private ActivityResultLauncher<PickVisualMediaRequest> pickLauncher;
    private Uri uri;
    private Boolean requested = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_edit, container, false);

        this.initial(view);
        this.onBind();

        return view;
    }

    private void initial(View view) {
        profile_editProfileTV = view.findViewById(R.id.profile_editProfileTV);
        profile_editUsernameTV = view.findViewById(R.id.profile_editUsernameTV);
        profile_editPasswordTV = view.findViewById(R.id.profile_editPasswordTV);
        profile_editEmailTV = view.findViewById(R.id.profile_editEmailTV);
        profile_editPhoneTV = view.findViewById(R.id.profile_editPhoneTV);
        profile_editAddressTV = view.findViewById(R.id.profile_editAddressTV);
        profile_editConfirmTV = view.findViewById(R.id.profile_editConfirmTV);

        profile_editUsernameET = view.findViewById(R.id.profile_editUsernameET);
        profile_editPasswordET = view.findViewById(R.id.profile_editPasswordET);
        profile_editEmailET = view.findViewById(R.id.profile_editEmailET);
        profile_editPhoneET = view.findViewById(R.id.profile_editPhoneET);
        profile_editAddressET = view.findViewById(R.id.profile_editAddressET);
        profile_editConfirmET = view.findViewById(R.id.profile_editConfirmET);

        profile_editProfileErrorTV = view.findViewById(R.id.profile_editProfileErrorTV);
        profile_editUsernameErrorTV = view.findViewById(R.id.profile_editUsernameErrorTV);
        profile_editPasswordErrorTV = view.findViewById(R.id.profile_editPasswordErrorTV);
        profile_editEmailErrorTV = view.findViewById(R.id.profile_editEmailErrorTV);
        profile_editPhoneErrorTV = view.findViewById(R.id.profile_editPhoneErrorTV);
        profile_editAddressErrorTV = view.findViewById(R.id.profile_editAddressErrorTV);
        profile_editConfirmErrorTV = view.findViewById(R.id.profile_editConfirmErrorTV);

        profile_editProfileIV = view.findViewById(R.id.profile_editProfileIV);

        profile_editRequestBtn = view.findViewById(R.id.profile_editRequestBtn);
        profile_editAcceptBtn = view.findViewById(R.id.profile_editAcceptBtn);
        profile_editCancelBtn = view.findViewById(R.id.profile_editCancelBtn);
        profile_editDeleteBtn = view.findViewById(R.id.profile_editDeleteBtn);
        profile_editSaveBtn = view.findViewById(R.id.profile_editSaveBtn);

        if (getArguments() != null) {
            String userId = getArguments().getString("user_id");

            DBUserManager userManager = new DBUserManager(requireContext());
            userManager.open();
            user = userManager.getUserById(userId);
            userManager.close();
        } else {
            Toast.makeText(requireContext(), "Error acquired! ProfileEditFragment @initial", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack();
        }

        pickLauncher = registerForActivityResult(
                new ActivityResultContracts.PickVisualMedia(),
                uri -> {
                    this.uri = uri;

                    if (uri != null) {
                        profile_editProfileIV.setImageURI(uri);
                    }
                }
        );
    }

    private void onBind() {
        profile_editProfileIV.setImageDrawable(user.getImage());
        profile_editUsernameET.setText(user.getUsername());
        profile_editPasswordET.setText(user.getPassword());
        profile_editEmailET.setText(user.getEmail());
        profile_editPhoneET.setText(user.getPhone());
        profile_editAddressET.setText(user.getAddress());

        profile_editProfileIV.setOnClickListener(this::onClick);
        profile_editRequestBtn.setOnClickListener(this::onClick);
        profile_editAcceptBtn.setOnClickListener(this::onClick);
        profile_editCancelBtn.setOnClickListener(this::onClick);
        profile_editDeleteBtn.setOnClickListener(this::onClick);
        profile_editSaveBtn.setOnClickListener(this::onClick);

        SessionManager sessionManager = new SessionManager(requireContext());
        session = sessionManager.getUser();

        if (user.getId().equals(session.getId())) {
            profile_editAcceptBtn.setVisibility(View.GONE);
            if (user.getRole() == User.Role.REQUESTED) {
                profile_editRequestBtn.setVisibility(View.GONE);
            } else if (user.getRole() != User.Role.CUSTOMER) {
                profile_editRequestBtn.setVisibility(View.GONE);
                profile_editCancelBtn.setVisibility(View.GONE);
            } else {
                profile_editCancelBtn.setVisibility(View.GONE);
            }
        } else if (session.getRole() == User.Role.ADMIN) {
            profile_editRequestBtn.setVisibility(View.GONE);
            profile_editConfirmTV.setVisibility(View.GONE);
            profile_editConfirmET.setVisibility(View.GONE);
            profile_editConfirmErrorTV.setVisibility(View.GONE);

            if (user.getRole() != User.Role.REQUESTED) {
                profile_editAcceptBtn.setVisibility(View.GONE);
                profile_editCancelBtn.setVisibility(View.GONE);
            }
        } else {
            profile_editPasswordTV.setVisibility(View.GONE);
            profile_editPasswordET.setVisibility(View.GONE);
            profile_editPasswordErrorTV.setVisibility(View.GONE);
            profile_editConfirmTV.setVisibility(View.GONE);
            profile_editConfirmET.setVisibility(View.GONE);
            profile_editConfirmErrorTV.setVisibility(View.GONE);
            profile_editRequestBtn.setVisibility(View.GONE);
            profile_editAcceptBtn.setVisibility(View.GONE);
            profile_editCancelBtn.setVisibility(View.GONE);
            profile_editSaveBtn.setVisibility(View.GONE);
            profile_editDeleteBtn.setVisibility(View.GONE);
        }
    }

    private void onClick(View view) {
        DBUserManager userManager = new DBUserManager(requireContext());

        if (view == profile_editSaveBtn) {
            this.clearError();

            String username = profile_editUsernameET.getText().toString();
            String password = profile_editPasswordET.getText().toString();
            String email = profile_editEmailET.getText().toString();
            String phone = profile_editPhoneET.getText().toString();
            String address = profile_editAddressET.getText().toString();
            String confirm = profile_editConfirmET.getText().toString();
            Drawable image;

            if (uri == null) {
                image = AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_person_100);
            } else {
                image = new ImageManager(this.uri, requireContext()).getImage();
            }

            User.Role role = (requested) ? User.Role.REQUESTED : user.getRole();

            if (validateInput(username, password, email, phone, confirm)) {
                userManager.open();
                userManager.updateUser(user.getId(), username, password, email, phone, address, image, role);
                userManager.close();

                requireActivity().getSupportFragmentManager().popBackStack();
            }
        } else if (view == profile_editRequestBtn) {
            if (this.requested) {
                Toast.makeText(requireContext(), "Already requested, please save changes!", Toast.LENGTH_SHORT).show();
            } else {
                this.requested = true;
            }
        } else if (view == profile_editProfileIV) {
            PickerManager pickerManager = new PickerManager(pickLauncher);
            pickerManager.pickImageOnly();
        } else if (view == profile_editAcceptBtn) {
            userManager.open();
            userManager.updateUser(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getPhone(), user.getAddress(), user.getImage(), User.Role.MERCHANT);
            userManager.close();

            requireActivity().getSupportFragmentManager().popBackStack();
        } else if (view == profile_editCancelBtn) {
            userManager.open();
            userManager.updateUser(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getPhone(), user.getAddress(), user.getImage(), User.Role.CUSTOMER);
            userManager.close();

            requireActivity().getSupportFragmentManager().popBackStack();
        } else if (view == profile_editDeleteBtn) {
            userManager.open();
            userManager.deleteUser(user);
            userManager.close();

            requireActivity().getSupportFragmentManager().popBackStack();
        }
    }

    private boolean validateInput(String username, String password, String email, String phone, String confirmPassword) {
        this.clearError();
        DBUserManager userManager = new DBUserManager(requireContext());
        userManager.open();
        boolean flag = true;

        // TODO color red when error
        // Username
        if (username.length() < 4 || username.length() > 20) {
            profile_editUsernameErrorTV.setText(R.string.auth_username_error_length);
            flag = false;
        } else if ((userManager.getUserByUsername(username) != null) && (!user.getUsername().equals(username))) {
            profile_editUsernameErrorTV.setText(R.string.auth_username_error_unique);
            flag = false;
        } else if (!Pattern.matches("^[a-zA-Z0-9]+$", username)) {
            profile_editUsernameErrorTV.setText(R.string.auth_username_error_regex);
            flag = false;
        }

        // Password
        if (password.length() < 8 || password.length() > 50) {
            profile_editPasswordErrorTV.setText(R.string.auth_password_error_length);
            flag = false;
        } else if (!Pattern.matches("^[a-zA-Z0-9]+$", password)) {
            profile_editPasswordErrorTV.setText(R.string.auth_password_error_regex);
            flag = false;
        }

        // Email
        if (!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", email)) {
            profile_editEmailErrorTV.setText(R.string.auth_email_error_regex);
            flag = false;
        }

        // Phone
        if (!phone.startsWith("0")) {
            profile_editPhoneErrorTV.setText(R.string.auth_phone_error_start);
            flag = false;
        } else if (phone.length() < 10 || phone.length() > 13) {
            profile_editPhoneErrorTV.setText(R.string.auth_phone_error_length);
            flag = false;
        }

        // Confirm Password
        if ((!confirmPassword.equals(user.getPassword())) & (session.getRole() != User.Role.ADMIN)) {
            profile_editConfirmErrorTV.setText(R.string.auth_credential_error);
            flag = false;
        }

        userManager.close();
        return flag;
    }

    private void clearError() {
        profile_editProfileErrorTV.setText("");
        profile_editUsernameErrorTV.setText("");
        profile_editPasswordErrorTV.setText("");
        profile_editEmailErrorTV.setText("");
        profile_editPhoneErrorTV.setText("");
        profile_editAddressErrorTV.setText("");
        profile_editConfirmErrorTV.setText("");
    }
}