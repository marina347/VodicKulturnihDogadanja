package hr.foi.vodickulturnihdogadanja.presenter;

import hr.foi.vodickulturnihdogadanja.model.UserModel;

/**
 * Created by Mateja on 25-Oct-17.
 */

public interface UserProfilePresenter {
    void tryViewData(int userId);
    void tryEditData(UserModel userModel);
}
