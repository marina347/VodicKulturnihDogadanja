package hr.foi.air.interactor.impl;

import android.util.Log;

import java.util.List;

import hr.foi.air.interactor.CallDefinitions;
import hr.foi.air.interactor.CommentInteractor;
import hr.foi.air.interactor.RetrofitREST;
import hr.foi.air.interactor.listener.CommentInteractorListener;
import hr.foi.air.model.CommentModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LEGION Y520 on 14.12.2017..
 */

public class CommentInteractorImpl implements CommentInteractor {
    CommentInteractorListener commentInteractorListener;

    /**
     * Implementacija funkcije sucelja CommentInteractor. Funkcija sluzi za postavljanje CommenntListenera.
     * @param commentListener
     */
    @Override
    public void setCommentListener(CommentInteractorListener commentListener) {
        this.commentInteractorListener=commentListener;
    }

    /**
     * Implementacija funkcije sucelja CommentInteractor. Funkcija sluzi za dohvacanje dogadaja sa servera.
     * @param eventId
     */
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

    /**
     * Implementacija funkcije sucelja CommentInteractor. Funkcija sluzi za slanje novog komentara na server.
     * @param comment
     */
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
