package fa.kz.aua.data

import fa.kz.aua.entity.Repository
import io.reactivex.Observable
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList
import java.util.concurrent.TimeUnit

class LocalRepository {

    fun getDataAsList(response: String, text: String): Observable<ArrayList<Repository>> {
        try {
            val json = JSONObject(response)
            val list = json.getJSONArray("list")
            val rList = ArrayList<Repository>()
            if (list != null) {
                for (i in 0 until list.length()) {
                    val `object` = list.getJSONObject(i)
                    val main = `object`.getJSONObject("main")
                    val name = `object`.getString("name")
                    if (name.toLowerCase().contains(text)) {
                        rList.add(
                            Repository(
                                name,
                                main.getInt("temp").toString()
                            )
                        )
                    }
                }
            }
            return Observable.just(rList).delay(1, TimeUnit.SECONDS)
        } catch (e: JSONException) {
            e.printStackTrace()
            return Observable.just(ArrayList<Repository>()).delay(1, TimeUnit.SECONDS)
        }
    }

}


