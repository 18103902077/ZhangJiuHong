package com.example.zuoye1;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
    String urlBanner="https://www.wanandroid.com/";
    @GET("banner/json")
    Observable<BannerBean> getBanners();

    String urlFood="http://www.qubaobei.com/";
    @GET("ios/cf/dish_list.php?stage_id=1&limit=20")
    Observable<FoodBean> getFoods(@Query("page") int page);

    String urlFoods="http://www.qubaobei.com/";
    @GET("ios/cf/dish_list.php?stage_id=1&limit=20&page=1")
    Observable<FoodBean> getFoods();

    String urlretrofit="http://yun918.cn/";
    @Multipart
    @POST("study/public/file_upload.php")
    Call<ResponseBody> getRetrofit(@Part("key") RequestBody requestBody, @Part MultipartBody.Part part);
}
