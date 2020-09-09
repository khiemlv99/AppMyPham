package com.example.quanlybanmyphamonline.Fragment;

import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlybanmyphamonline.Adapter.RecyclerviewTrangChuAdapter;
import com.example.quanlybanmyphamonline.Adapter.SliderPagerAdapter;
import com.example.quanlybanmyphamonline.Adapter.VerticalRecyclerViewAdapter;
import com.example.quanlybanmyphamonline.Class.HorizontalModel;
import com.example.quanlybanmyphamonline.Class.Server;
import com.example.quanlybanmyphamonline.Class.VerticalModel;
import com.example.quanlybanmyphamonline.Class.checkconnection;
import com.example.quanlybanmyphamonline.R;
import com.example.quanlybanmyphamonline.decoration.BannerSlider;
import com.example.quanlybanmyphamonline.decoration.SliderIndicator;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrangChuFragment extends Fragment {

    ViewFlipper viewFlipper;
    Animation in, out;
    RecyclerView verticalRecyclerView;
    VerticalRecyclerViewAdapter adapter;
    RecyclerviewTrangChuAdapter lstviewAdapter;
    ArrayList<VerticalModel> arrayListVertical;
    ArrayList<HorizontalModel> horizontalModelArrayList, arrListView;
    VerticalModel verticalModel;
    RecyclerView listView;
    ArrayList<String> arrayHinh;
    LottieAnimationView lottieAnimationView;
    Toolbar toolbar;
    private SliderPagerAdapter mAdapter;
    private SliderIndicator mIndicator;

    private BannerSlider bannerSlider;
    private LinearLayout mLinearLayout;


    public TrangChuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trang_chu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        arrayHinh = new ArrayList<>();
        arrayHinh.add("https://www.sahadeal.vn/upload/team/2017/0313/sahadeal.vn-14893650171997.jpg");
        arrayHinh.add("https://product.hstatic.net/1000186358/product/mascara_perfect_pro_double_protection_3_2d7861beed4648aeba7c1a0a03900f07.jpg");
        AnhXa();

        if (checkconnection.haveNetworkConnection(getContext())) {

            Recyclerview();
            ListView();
            setupSlider();
            lottieAnimationView.setVisibility(View.GONE);

        } else {
            checkconnection.Toast_Short(getContext(), "Hãy kiểm tra lại kết nối ");
            lottieAnimationView.setVisibility(View.VISIBLE);


        }


    }

    public void TaoPlipper() {

        for (int i = 0; i < arrayHinh.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            Picasso.get().load(arrayHinh.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }

        in = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        out = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        viewFlipper.setInAnimation(in);
        viewFlipper.setOutAnimation(out);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
    }

    public void AnhXa() {
        verticalRecyclerView = getView().findViewById(R.id.recyclerView);
        bannerSlider = getView().findViewById(R.id.sliderView);
        mLinearLayout = getView().findViewById(R.id.pagesContainer);
        listView= getView().findViewById(R.id.listviewSanPhamBanChay);
        lottieAnimationView = getView().findViewById(R.id.lottie_nointernet);

    }

    public void Recyclerview() {
        arrayListVertical = new ArrayList<>();
        verticalRecyclerView.setHasFixedSize(true);
        verticalRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter = new VerticalRecyclerViewAdapter(getContext(), arrayListVertical);
        verticalRecyclerView.setAdapter(adapter);
        setData();
    }


    private void setData() {
        verticalModel = new VerticalModel();
        verticalModel.setTilte("Sản phẩm bán chạy");

        horizontalModelArrayList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongDanSanPhammoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject jsonObject = response.getJSONObject(i);
//                        Toast.makeText(getContext(), jsonObject.getString("tensp"), Toast.LENGTH_SHORT).show();
                        int masanpham = jsonObject.getInt("masp");
                        String tensp = jsonObject.getString("tensp");

                        String mota = jsonObject.getString("mota");
                        int gia = jsonObject.getInt("gia");
                        int maloaisanpham = jsonObject.getInt("MaLoaiSanPham");
                        String hinh = jsonObject.getString("hinh");


                        HorizontalModel horizontalModel = new HorizontalModel();
                        horizontalModel.setGia(gia);
                        horizontalModel.setMota(mota);
                        horizontalModel.setTen(tensp);
                        horizontalModel.setHinh(hinh);
                        horizontalModel.setMaloaisp(maloaisanpham);
                        horizontalModel.setMasp(masanpham);

                        horizontalModelArrayList.add(horizontalModel);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);


        verticalModel.setArrayList(horizontalModelArrayList);
        arrayListVertical.add(verticalModel);
        adapter.notifyDataSetChanged();
    }


    public void ListView() {
        arrListView=new ArrayList<>();
        lstviewAdapter = new RecyclerviewTrangChuAdapter(getContext(), arrListView);
        listView.setHasFixedSize(true);

        listView.setLayoutManager(new GridLayoutManager(getContext(),2));
        listView.setAdapter(lstviewAdapter);
        TaoDuLieuListView();
    }

    public void TaoDuLieuListView() {

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongDanSanPhammoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject jsonObject = response.getJSONObject(i);
                        int masanpham = jsonObject.getInt("masp");
                        String tensp = jsonObject.getString("tensp");
                        String mota = jsonObject.getString("mota");
                        int gia = jsonObject.getInt("gia");
                        int maloaisanpham = jsonObject.getInt("MaLoaiSanPham");
                        String hinh = jsonObject.getString("hinh");


                        HorizontalModel horizontalModel = new HorizontalModel();
                        horizontalModel.setGia(gia);
                        horizontalModel.setMota("Mô tả sản phẩm: " + mota);
                        horizontalModel.setTen(tensp);
                        horizontalModel.setHinh(hinh);
                        horizontalModel.setMaloaisp(maloaisanpham);
                        horizontalModel.setMasp(masanpham);

                        arrListView.add(horizontalModel);
                        lstviewAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void setupSlider() {
        bannerSlider.setDurationScroll(800);
        List<Fragment> fragments = new ArrayList<>();
        //link image
        fragments.add(FragmentSlider.newInstance("https://ecs7.tokopedia.net/img/banner/2020/4/19/85531617/85531617_17b56894-2608-4509-a4f4-ad86aa5d3b62.jpg"));
        fragments.add(FragmentSlider.newInstance("https://ecs7.tokopedia.net/img/banner/2020/4/19/85531617/85531617_7da28e4c-a14f-4c10-8fec-845578f7d748.jpg"));
        fragments.add(FragmentSlider.newInstance("https://ecs7.tokopedia.net/img/banner/2020/4/18/85531617/85531617_87a351f9-b040-4d90-99f4-3f3e846cd7ef.jpg"));
        fragments.add(FragmentSlider.newInstance("https://ecs7.tokopedia.net/img/banner/2020/4/20/85531617/85531617_03e76141-3faf-45b3-8bcd-fc0962836db3.jpg"));

        mAdapter = new SliderPagerAdapter(getActivity().getSupportFragmentManager(), fragments);
        bannerSlider.setAdapter(mAdapter);
        mIndicator = new SliderIndicator(getContext(), mLinearLayout, bannerSlider, R.drawable.indicator_circle);
        mIndicator.setPageCount(fragments.size());
        mIndicator.show();
    }

    private void startAnimationLip() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lottieAnimationView.setProgress((Float) animation.getAnimatedValue());

            }
        });
        if (lottieAnimationView.getProgress() == 0f) {
            animator.start();
        } else {
            lottieAnimationView.setProgress(0f);
        }
    }
}

