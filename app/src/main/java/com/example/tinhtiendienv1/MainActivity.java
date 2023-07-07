package com.example.tinhtiendienv1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edtChiSoMoi, edtChiSoCu, edtSoHo; // EditText để nhập dữ liệu
    TextView txtKQ, txtSokWh; // TextView để hiển thị kết quả và số kWh

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls(); // Khởi tạo và gán các control từ layout
        addListeners(); // Thêm các listener cho các EditText và Button
    }

    private void addControls() {
        edtChiSoCu = findViewById(R.id.edtChiSoCu);
        edtChiSoMoi = findViewById(R.id.edtChiSoMoi);
        edtSoHo = findViewById(R.id.edtSoHo);
        txtKQ = findViewById(R.id.txtKQ);
        txtSokWh = findViewById(R.id.txtSokWh);
        edtChiSoCu.requestFocus();
    }

    private void addListeners() {
        // Thêm TextWatcher để theo dõi sự thay đổi của EditText
        edtChiSoCu.addTextChangedListener(textWatcher);
        edtChiSoMoi.addTextChangedListener(textWatcher);
        edtSoHo.addTextChangedListener(textWatcher);

        // Thêm sự kiện OnEditorActionListener để xử lý khi nhập xong trên bàn phím
        edtChiSoMoi.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                refreshSokWh(); // Gọi hàm refreshSokWh khi nhập xong
                return true;
            }
            return false;
        });

        edtChiSoCu.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                refreshSokWh(); // Gọi hàm refreshSokWh khi nhập xong
                return true;
            }
            return false;
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Không cần xử lý trước khi giá trị thay đổi
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Khi giá trị thay đổi, gọi hàm refreshSokWh
            refreshSokWh();
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Không cần xử lý sau khi giá trị thay đổi
        }
    };

    public void refreshSokWh() {
        String chiSoCuStr = edtChiSoCu.getText().toString();
        String chiSoMoiStr = edtChiSoMoi.getText().toString();
        String soHoStr = edtSoHo.getText().toString();

        if (chiSoCuStr.isEmpty() || chiSoMoiStr.isEmpty() || soHoStr.isEmpty()) {
            txtKQ.setText("");
            txtSokWh.setText("Số kWh dùng:");
            return;
        }

        int chiSoCu = Integer.parseInt(chiSoCuStr);
        int chiSoMoi = Integer.parseInt(chiSoMoiStr);
        int soHo = Integer.parseInt(soHoStr);

        if (chiSoMoi < chiSoCu) {
            Toast.makeText(this, "Chỉ số mới phải lớn hơn chỉ số cũ", Toast.LENGTH_SHORT).show();
            txtKQ.setText("");
            txtSokWh.setText("Số kWh dùng:");
            return;
        }

        Integer kq;
        if ((chiSoMoi - chiSoCu) <= (50 * soHo)) {
            kq = (chiSoMoi - chiSoCu) * 1484;
        } else if ((chiSoMoi - chiSoCu) <= (100 * soHo)) {
            kq = 50 * soHo * 1484 + (chiSoMoi - chiSoCu - 50 * soHo) * 1533;
        } else if ((chiSoMoi - chiSoCu) <= (200 * soHo)) {
            kq = 50 * soHo * 1484 + 50 * soHo * 1533 + (chiSoMoi - chiSoCu - 100 * soHo) * 1786;
        } else if ((chiSoMoi - chiSoCu) <= (300 * soHo)) {
            kq = 50 * soHo * 1484 + 50 * soHo * 1533 + 100 * soHo * 1786 + (chiSoMoi - chiSoCu - 200 * soHo) * 2242;
        } else if ((chiSoMoi - chiSoCu) <= (400 * soHo)) {
            kq = 50 * soHo * 1484 + 50 * soHo * 1533 + 100 * soHo * 1786 + 100 * soHo * 2242 + (chiSoMoi - chiSoCu - 300 * soHo) * 2503;
        } else {
            kq = 50 * soHo * 1484 + 50 * soHo * 1533 + 100 * soHo * 1786 + 100 * soHo * 2242 + 100 * soHo * 2503 + (chiSoMoi - chiSoCu - 400 * soHo) * 2587;
        }

        String show = "Tổng số tiền điện giá sinh hoạt (" + soHo + " hộ):\n" + kq;
        txtKQ.setText(show);
        txtSokWh.setText("Số kWh dùng:"+String.valueOf(chiSoMoi - chiSoCu));
    }

    public void xuLySHBT(View view) {
        String chiSoCuStr = edtChiSoCu.getText().toString();
        String chiSoMoiStr = edtChiSoMoi.getText().toString();

        if (chiSoCuStr.isEmpty() || chiSoMoiStr.isEmpty()) {
            txtKQ.setText("");
            txtSokWh.setText("Số kWh dùng:");
            return;
        }

        int chiSoCu = Integer.parseInt(chiSoCuStr);
        int chiSoMoi = Integer.parseInt(chiSoMoiStr);

        if (chiSoMoi < chiSoCu) {
            Toast.makeText(this, "Chỉ số mới phải lớn hơn chỉ số cũ", Toast.LENGTH_SHORT).show();
            txtKQ.setText("");
            txtSokWh.setText("Số kWh dùng:");
            return;
        }

        Integer kq = (chiSoMoi - chiSoCu) * 2320;
        String show = "Tổng số tiền điện giá kinh doanh:\n" + kq;
        txtKQ.setText(show);
        txtSokWh.setText("Số kWh dùng:"+ String.valueOf(chiSoMoi - chiSoCu));
    }

    public void xuLyKinhDoanh(View view) {
        String chiSoCuStr = edtChiSoCu.getText().toString();
        String chiSoMoiStr = edtChiSoMoi.getText().toString();

        if (chiSoCuStr.isEmpty() || chiSoMoiStr.isEmpty()) {
            txtKQ.setText("");
            txtSokWh.setText("Số kWh dùng:");
            return;
        }

        int chiSoCu = Integer.parseInt(chiSoCuStr);
        int chiSoMoi = Integer.parseInt(chiSoMoiStr);

        if (chiSoMoi < chiSoCu) {
            Toast.makeText(this, "Chỉ số mới phải lớn hơn chỉ số cũ", Toast.LENGTH_SHORT).show();
            txtKQ.setText("");
            txtSokWh.setText("Số kWh dùng:");
            return;
        }

        Integer kq = (chiSoMoi - chiSoCu) * 2320;
        String show = "Tổng số tiền điện giá kinh doanh:\n" + kq;
        txtKQ.setText(show);
        txtSokWh.setText("Số kWh dùng:"+String.valueOf(chiSoMoi - chiSoCu));
    }

    public void xuLySanXuat(View view) {
        String chiSoCuStr = edtChiSoCu.getText().toString();
        String chiSoMoiStr = edtChiSoMoi.getText().toString();

        if (chiSoCuStr.isEmpty() || chiSoMoiStr.isEmpty()) {
            txtKQ.setText("");
            txtSokWh.setText("Số kWh dùng:");
            return;
        }

        int chiSoCu = Integer.parseInt(chiSoCuStr);
        int chiSoMoi = Integer.parseInt(chiSoMoiStr);

        if (chiSoMoi < chiSoCu) {
            Toast.makeText(this, "Chỉ số mới phải lớn hơn chỉ số cũ", Toast.LENGTH_SHORT).show();
            txtKQ.setText("");
            txtSokWh.setText("Số kWh dùng:");
            return;
        }

        Integer kq = (chiSoMoi - chiSoCu) * 1518;
        String show = "Tổng số tiền điện giá sản xuất:\n" + kq;
        txtKQ.setText(show);
        txtSokWh.setText("Số kWh dùng:"+String.valueOf(chiSoMoi - chiSoCu));
    }

    public void xuLyXoa(View view) {
        edtChiSoCu.setText("");
        edtChiSoMoi.setText("");
        edtSoHo.setText("");
        txtKQ.setText("Tổng số tiền điện:");
        txtSokWh.setText("Số kWh dùng:");
        edtChiSoCu.requestFocus();
    }

    public void xuLyThoat(View view) {
        finish();
    }
}
