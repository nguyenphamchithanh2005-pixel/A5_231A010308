package vn.edu.vhu.ltdd.a5intent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "A5_231A010308";

    private Contact contact;
    private EditText edtHoTenMoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "B: onCreate");

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        TextView tvThongTin = findViewById(R.id.tvThongTin);
        TextView tvNguoiGui = findViewById(R.id.tvNguoiGui);
        edtHoTenMoi = findViewById(R.id.edtHoTenMoi);
        Button btnLuu = findViewById(R.id.btnLuu);
        Button btnHuy = findViewById(R.id.btnHuy);

        // 1. Lấy dữ liệu 1 Contact đơn lẻ
        contact = IntentCompat.getParcelableExtra(getIntent(),
                MainActivity.EXTRA_CONTACT, Contact.class);
        String nguoiGui = getIntent().getStringExtra(MainActivity.EXTRA_NGUOI_GUI);

        // 2. Lấy Danh sách Contact (Mở rộng)
        ListView lvDanhSach = findViewById(R.id.lvDanhSach);
        ArrayList<Contact> danhSach = IntentCompat.getParcelableArrayListExtra(
                getIntent(),
                MainActivity.EXTRA_DANH_SACH,
                Contact.class
        );

        if (danhSach != null && lvDanhSach != null) {
            ArrayList<String> tenHienThi = new ArrayList<>();
            for (Contact c : danhSach) {
                tenHienThi.add(c.getHoTen() + " - " + c.getDienThoai());
            }
            lvDanhSach.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tenHienThi));
        }

        if (contact == null) {                       // luôn phòng trường hợp không nhận được dữ liệu
            tvThongTin.setText(R.string.no_data);
            Log.w(TAG, "Không nhận được Contact từ Intent");
            return;
        }

        tvThongTin.setText(getString(R.string.detail_format,
                contact.getHoTen(), contact.getDienThoai(), contact.getEmail()));
        tvNguoiGui.setText(getString(R.string.sent_by, nguoiGui));
        edtHoTenMoi.setText(contact.getHoTen());

        btnLuu.setOnClickListener(v -> luuVaQuayLai());
        btnHuy.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);              // báo cho màn hình trước biết là đã hủy
            finish();
        });
    }

    // ============ CÁC HÀM VÒNG ĐỜI MÀN HÌNH B ============

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "B: onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "B: onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "B: onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "B: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "B: onDestroy");
    }

    /** Trả dữ liệu đã sửa về màn hình gọi. */
    private void luuVaQuayLai() {
        String hoTenMoi = edtHoTenMoi.getText().toString().trim();
        if (hoTenMoi.isEmpty()) {
            edtHoTenMoi.setError(getString(R.string.err_empty));
            return;
        }
        contact.setHoTen(hoTenMoi);

        Intent ketQua = new Intent();
        ketQua.putExtra(MainActivity.EXTRA_CONTACT, contact);
        setResult(RESULT_OK, ketQua);
        Log.d(TAG, "Trả kết quả về: " + hoTenMoi);
        finish();                                    // đóng màn hình này, quay về màn hình trước
    }
}