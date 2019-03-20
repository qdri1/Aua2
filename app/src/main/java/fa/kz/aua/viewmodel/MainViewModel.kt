package fa.kz.aua.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import android.view.Gravity
import android.widget.Toast
import fa.kz.aua.data.ApiService
import fa.kz.aua.data.LocalRepository
import fa.kz.aua.entity.Repository
import fa.kz.aua.util.DataUtil
import fa.kz.aua.util.Prefs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService by lazy {
        ApiService.create()
    }

    private val localRepository: LocalRepository = LocalRepository()

    val text = ObservableField<String>()

    var isLoading = ObservableField<Boolean>()

    var repositories = MutableLiveData<ArrayList<Repository>>()

    private val compositeDisposable = CompositeDisposable()

    fun loadRepositories() {
        isLoading.set(true)
        compositeDisposable.add(
            apiService.getData(DataUtil.getIds(), "metric", "07ed2f9abc0a3eaccd0a9aff406b4532")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        Prefs.setResponse(getApplication(), result.string())
                        isLoading.set(false)
                    },
                    { error ->
                        println("###$error")
                    }
                )
        )
    }

    fun getSearchedData(text: String) {
        if (text.length > 2) {
            val str = Prefs.getResponse(getApplication())
            if (str != null) {
                isLoading.set(true)
                compositeDisposable.add(
                    localRepository.getDataAsList(str, text)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableObserver<ArrayList<Repository>>() {

                            override fun onError(e: Throwable) {
                                // error
                            }

                            override fun onNext(data: ArrayList<Repository>) {
                                repositories.value = data
                            }

                            override fun onComplete() {
                                isLoading.set(false)
                            }
                        })
                )
            } else {
                val toast =
                    Toast.makeText(getApplication(), "Что то не так", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
        } else {
            val toast =
                Toast.makeText(getApplication(), "Введите еще " + (3 - text.length) + " символ!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

}