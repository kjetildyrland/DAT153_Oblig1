package no.hvl.dat153.namequiz;

import android.os.AsyncTask;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class QuizRepository {

    private MutableLiveData<List<Person>> searchResults =
            new MutableLiveData<>();

    private void asyncFinished(List<Person> results) {
        searchResults.setValue(results);
    }

    private static class QueryAsyncTask extends
            AsyncTask<String, Void, List<Person>> {

        private PersonDao asyncTaskDao;
        private QuizRepository delegate = null;

        QueryAsyncTask(PersonDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected List<Person> doInBackground(final String... params) {
            return asyncTaskDao.findPerson(params[0]);
        }

        @Override
        protected void onPostExecute(List<Person> result) {
            delegate.asyncFinished(result);
        }
    }

    private static class InsertAsyncTask extends AsyncTask<Person, Void, Void> {

        private PersonDao asyncTaskDao;

        InsertAsyncTask(PersonDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Person... params) {
            asyncTaskDao.insertPerson(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Person, Void, Void> {

        private PersonDao asyncTaskDao;

        DeleteAsyncTask(PersonDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Person... params) {
            asyncTaskDao.deletePerson(params[0]);
            return null;
        }
    }
}