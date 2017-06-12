package com.achievers.AddAchievement;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.achievers.R;
import com.achievers.data.Achievement;
import com.achievers.data.Involvement;
import com.achievers.databinding.AddAchievementFragBinding;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class AddAchievementFragment extends Fragment implements AddAchievementContract.View {

    private AddAchievementContract.Presenter mPresenter;

    private EditText mEtTitle;
    private EditText mEtDescription;
    private Spinner mSpInvolvement;
//    private Button mBtnTakePicture;
//    private Button mBtnChooseExisting;
//    private ImageView mIvTakenPhoto;

    private AddAchievementFragBinding mViewDataBinding;

    public static AddAchievementFragment newInstance() {
        return new AddAchievementFragment();
    }

    public AddAchievementFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull AddAchievementContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_add_achievement);
        fab.setImageResource(R.drawable.ic_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mEtTitle.getText().toString();
                String description = mEtDescription.getText().toString();
                String involvement = mSpInvolvement.getSelectedItem().toString();
//                Integer cateogryId = mCategoryId;

                Achievement achievement = new Achievement();//title, description, involvement, equipmentId);

                if (!mPresenter.validateAchievement(achievement)) {
                    // TODO: compose proper message
                    showInvalidAchievementMessage("Please fill required fields.");
                    return;
                }

                // hide keyboard before finish so as not to break previous Activity layout
                View view = getActivity().getCurrentFocus();

                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                mPresenter.saveAchievement(achievement);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (mViewDataBinding == null) {
            mViewDataBinding = AddAchievementFragBinding.inflate(inflater, container, false);
        }

        mEtTitle = mViewDataBinding.etTitle;
        mEtDescription = mViewDataBinding.etDescription;
        mSpInvolvement = mViewDataBinding.spInvolvement;
//        mBtnTakePicture = mViewDataBinding.btnTakePicture;
//        mBtnChooseExisting = mViewDataBinding.btnChooseExisting;
//        mIvTakenPhoto = mViewDataBinding.ivTakePicture;

        setHasOptionsMenu(true);
        // Fragment is retained simply to persist the edits after rotation.
        setRetainInstance(true);

        return mViewDataBinding.getRoot();
    }

    @Override
    public void finishActivity() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void showInvolvements(Involvement[] involvements) {
        this.mSpInvolvement.setAdapter(new ArrayAdapter<>(getContext(), R.layout.simple_spinner_item, involvements));
    }

    @Override
    public void showInvalidAchievementMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setAchievement(Achievement achievement) {
        mViewDataBinding.setAchievement(achievement);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}