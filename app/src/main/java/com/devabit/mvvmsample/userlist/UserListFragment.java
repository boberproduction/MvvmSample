package com.devabit.mvvmsample.userlist;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.devabit.mvvmsample.R;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class UserListFragment extends Fragment {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private UserListViewModel userListViewModel;
    private ListView usersList;
    private ProgressBar progressBar;

    public static UserListFragment newInstance() {
        return new UserListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userListViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(UserListViewModel.class);

        userListViewModel.getUserList().observe(this,
                users -> usersList.setAdapter(new UserListAdapter(getActivity(), 0, users)));

        userListViewModel.getProgressBarActiveStatus().observe(this,
                this::showProgressBar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment_layout, container, false);

        Button refreshButton = view.findViewById(R.id.refreshButton);
        usersList = view.findViewById(R.id.listView);
        progressBar = view.findViewById(R.id.progressBar);

        refreshButton.setOnClickListener(v -> userListViewModel.refreshButtonClick());

        return view;
    }

    private void showProgressBar(boolean toggle) {
        if (progressBar != null) {
            progressBar.setVisibility(toggle ? View.VISIBLE : View.GONE);
        }
    }
}
