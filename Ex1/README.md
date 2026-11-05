# [BÀI TẬP 1 - KHÁ] THIẾT KẾ TÀI NGUYÊN THEO NAMING CONVENTION

## 1. Mục tiêu kiến thức & Kỹ năng
- **Kiến thức:** Hiểu và áp dụng đúng quy tắc đặt tên tài nguyên (Resources) trong kiến trúc RESTful API theo chuẩn quốc tế (Lesson 04).
- **Kỹ năng:** Chuyển đổi yêu cầu nghiệp vụ quản lý bệnh viện thành các Endpoints chuẩn RESTful, thân thiện với hệ thống phân tán và các client tiêu thụ (Web, Mobile, Third-party).

---

## 2. Bảng tổng hợp 5 URL chuẩn RESTful cho tài nguyên "Bác sĩ" (doctors)

| STT | Thao tác nghiệp vụ | HTTP Method | RESTful Endpoint | Status Code thành công | Mô tả & Payload |
|:---:|:---|:---:|:---|:---:|:---|
| **1** | **Lấy danh sách toàn bộ bác sĩ** | `GET` | `/api/v1/doctors` | `200 OK` | Lấy danh sách tài nguyên danh từ số nhiều; hỗ trợ query params để phân trang/lọc: `/api/v1/doctors?page=0&size=10`. |
| **2** | **Lấy thông tin chi tiết một bác sĩ theo ID** | `GET` | `/api/v1/doctors/{id}` | `200 OK` *(hoặc `404 Not Found` nếu không có)* | Lấy chi tiết tài nguyên cụ thể qua định danh ID trong URI path. |
| **3** | **Thêm mới một bác sĩ** | `POST` | `/api/v1/doctors` | `201 Created` | Tạo mới tài nguyên. Request Body chứa JSON thông tin bác sĩ. Response trả về bản ghi vừa tạo kèm Header `Location`. |
| **4** | **Cập nhật thông tin bác sĩ** | `PUT` | `/api/v1/doctors/{id}` | `200 OK` *(hoặc `204 No Content`)* | Cập nhật/thay thế toàn bộ thông tin của bác sĩ có mã `{id}` bằng dữ liệu trong Request Body. |
| **5** | **Xóa bác sĩ khỏi hệ thống** | `DELETE` | `/api/v1/doctors/{id}` | `204 No Content` *(hoặc `200 OK`)* | Xóa tài nguyên có mã `{id}`. Trả về `204 No Content` (không có body) khi xóa thành công. |

---

## 3. Các nguyên tắc Naming Convention chuẩn RESTful đã áp dụng

1. **Sử dụng danh từ số nhiều (Plural Nouns) đại diện cho Collection:**
   - Dùng `/doctors` thay vì `/doctor`. Trong thế giới thực, một tập hợp chứa nhiều thực thể bác sĩ. Khi truy vấn một bác sĩ cụ thể, ta truy vấn phần tử nằm trong tập hợp đó: `/doctors/{id}`.
2. **Tuyệt đối KHÔNG sử dụng động từ (Verbs) trên URI:**
   - **Sai lầm phổ biến (Anti-patterns):**
     - ❌ `GET /getAllDoctors` hoặc `GET /api/getDoctors`
     - ❌ `GET /doctor/detail?id=1`
     - ❌ `POST /createDoctor` hoặc `POST /doctor/add`
     - ❌ `POST /updateDoctorById/1`
     - ❌ `GET /deleteDoctor/1` hoặc `POST /removeDoctor?id=1`
   - **Thiết kế đúng chuẩn RESTful:**
     - Hành động (Action) được định nghĩa bởi **HTTP Method** (`GET`, `POST`, `PUT`, `DELETE`).
     - Đường dẫn URI chỉ đóng vai trò định danh **Tài nguyên** (Resource).
3. **Tiền tố phiên bản API (API Versioning):**
   - Đặt `/api/v1/doctors` để đảm bảo tính tương thích ngược (Backward Compatibility) khi hệ thống nâng cấp.
4. **Quy tắc chữ thường và dấu gạch nối (kebab-case):**
   - Luôn sử dụng chữ thường (lowercase) cho URI. Nếu tài nguyên gồm nhiều từ thì dùng dấu gạch nối `-` (ví dụ: `/doctor-appointments`), tránh dùng `camelCase` hay `snake_case`.

---

## 4. Chạy và kiểm thử mã nguồn đính kèm
Dự án Spring Boot mẫu kèm theo đã cấu hình đầy đủ Controller và Unit Test cho cả 5 Endpoint:
```bash
cd M:\JAVA_BACKEND\BTVN_moi\SS10\Ex1
mvn test
```
