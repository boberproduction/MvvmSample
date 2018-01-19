package com.devabit.mvvmsample;

import com.devabit.mvvmsample.model.MockRepository;
import com.devabit.mvvmsample.model.Repository;
import com.devabit.mvvmsample.userlist.UserListViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserListViewModelTest {
    private UserListViewModel userListViewModel;
    @Spy
    private Repository repository = new MockRepository();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userListViewModel = new UserListViewModel(repository);
    }

    @Test
    public void buttonClickSendsDataRequest() {
        userListViewModel.refreshButtonClick();

        verify(repository).getUsers();
    }
}
