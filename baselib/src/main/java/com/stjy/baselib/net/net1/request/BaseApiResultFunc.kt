package com.stjy.baselib.net.net1.request

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.zhouyou.http.model.ApiResult
import com.zhouyou.http.utils.Utils
import io.reactivex.functions.Function
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.reflect.Modifier
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author dai
 */
class BaseApiResultFunc<T>(private var type: Type) : Function<ResponseBody, ApiResult<T?>> {

    var gson: Gson

    init {
        gson = GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls()
                .create()
    }

    @Throws(Exception::class)
    override fun apply(responseBody: ResponseBody): ApiResult<T?> {
        var apiResult = ApiResult<T?>()
        apiResult.code = 0
        if (type is ParameterizedType) { //自定义ApiResult
            val cls: Class<T?> = (type as ParameterizedType).rawType as Class<T?>
            if (ApiResult::class.java.isAssignableFrom(cls)) {
                val params = (type as ParameterizedType).actualTypeArguments
                val clazz = Utils.getClass(params[0], 0)
                val rawType = Utils.getClass(type, 0)
                try {
                    val json = responseBody.string()
                    //增加是List<String>判断错误的问题
                    if (!MutableList::class.java.isAssignableFrom(rawType) && clazz == String::class.java) {
                        /*apiResult.setData((T) json);
                        apiResult.setCode("200");*/
                        val type = Utils.getType(cls, 0)
                        val result = gson.fromJson<ApiResult<T?>>(json, type)
                        if (result != null) {
                            apiResult = result
                            if (result.data != null) {
                                apiResult.setData(result.data as T)
                            } else {
                                apiResult.setData(json as T)
                            }
                        } else {
                            apiResult.setMsg("json is null")
                        }
                    } else {
                        val result = gson.fromJson<ApiResult<T?>>(json, type)
                        if (result != null) {
                            apiResult = result
                        } else {
                            apiResult.setMsg("json is null")
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    apiResult.setMsg(e.message)
                } finally {
                    responseBody.close()
                }
            } else {
                apiResult.setMsg("ApiResult.class.isAssignableFrom(cls) err!")
            }
        } else { //默认Apiresult
            try {
                val json = responseBody.string()
                val clazz: Class<T> = Utils.getClass(type, 0) as Class<T>
                if (clazz == String::class.java) {
                    apiResult.data = json as T
                    apiResult.setCode(0)
                    /*final ApiResult result = parseApiResult(json, apiResult);
                    if (result != null) {
                        apiResult = result;
                        apiResult.setData((T) json);
                    } else {
                        apiResult.setMsg("json is null");
                    }*/
                } else {
                    val result = parseApiResult(json, apiResult)
                    if (result != null) {
                        apiResult = result
                        if (apiResult.data != null) {
                            val data = gson.fromJson(apiResult.data.toString(), clazz)
                            apiResult.setData(data)
                        } else {
                            apiResult.setMsg("ApiResult's data is null")
                        }
                    } else {
                        apiResult.setMsg("json is null")
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                apiResult.setMsg(e.message)
            } catch (e: IOException) {
                e.printStackTrace()
                apiResult.setMsg(e.message)
            } finally {
                responseBody.close()
            }
        }
        return apiResult
    }

    @Throws(JSONException::class)
    private fun parseApiResult(json: String, apiResult: ApiResult<T?>): ApiResult<T?>? {
        if (TextUtils.isEmpty(json)) {
            return null
        }
        return with(apiResult) {
            val jsonObject = JSONObject(json)
            if (jsonObject.has("code")) {
                this.code = jsonObject.getInt("code")
            }
            if (jsonObject.has("data")) {
                this.data = jsonObject.getString("data") as T
            }
            if (jsonObject.has("msg")) {
                this.msg = jsonObject.getString("msg")
            }
            this
        }
    }
}