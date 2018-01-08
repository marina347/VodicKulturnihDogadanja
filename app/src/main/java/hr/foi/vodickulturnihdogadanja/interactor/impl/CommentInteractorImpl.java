package hr.foi.vodickulturnihdogadanja.interactor.impl;

import android.util.Log;

import java.util.List;

import hr.foi.vodickulturnihdogadanja.interactor.CallDefinitions;
import hr.foi.vodickulturnihdogadanja.interactor.CommentInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.RetrofitREST;
import hr.foi.vodickulturnihdogadanja.interactor.listener.CommentInteractorListener;
import hr.foi.vodickulturnihdogadanja.model.CommentModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LEGION Y520 on 14.12.2017..
 */

public class CommentInteractorImpl implements CommentInteractor {
    CommentInteractorListener commentInteractorListener;

    @Override
    public void setCommentListener(CommentInteractorListener commentListener) {
        this.commentInteractorListener=commentListener;
    }

    @Override
    public void getComments(int eventId) {
        CallDefinitions calls = RetrofitREST.getRetrofit().create(CallDefinitions.class);
        Call<List<CommentModel>> call = calls.getComment(eventId);
        call.enqueue(new Callback<List<CommentModel>>() {
            @Override
            public void onResponse(Call<List<CommentModel>> call, Response<List<CommentModel>> response) {
                List<CommentModel> list=response.body();
                if (list == null){
                    commentInteractorListener.NoComment();
                }else {
                    commentInteractorListener.ArrivedComments(list);
                }
            }

            @Override
            public void onFailure(Call<List<CommentModel>> call, Throwable t) {

            }
        });

    }

    @Override
    public void CreateNewComment(CommentModel comment) {
        CallDefinitions calls = RetrofitREST.getRetrofit().create(CallDefinitions.class);
        Call<CommentModel> call = calls.createNewComment(comment);
        call.enqueue(new Callback<CommentModel>() {
            @Override
            public void onResponse(Call<CommentModel> call, Response<CommentModel> response) {
                if(response.isSuccessful()){
                    CommentModel comment = response.body();
                    commentInteractorListener.onSuccessCreateNewComment(comment);
                }
                else {
                    Log.d("Api", "failed creeateNewComment");
                }
            }

            @Override
            public void onFailure(Call<CommentModel> call, Throwable t) {
                Log.d("Api", t.getMessage());
                commentInteractorListener.onFailedCreateNewComment("Komentar nije kreiran!");
            }
        });
    }
}
