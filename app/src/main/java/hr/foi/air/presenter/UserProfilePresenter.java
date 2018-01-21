package hr.foi.air.presenter;

import hr.foi.air.model.UserModel;

/**
 * Created by Mateja on 25-Oct-17.
 */

public interface UserProfilePresenter {
    void tryViewData(int userId);
    void tryEditData(UserModel userModel);
}
