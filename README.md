# ỨNG DỤNG TAO ĐỀ THI TRẮC NGHIỆM TỰ ĐỘNG
## Cơ sở dữ liệu demo tạo đề môn Toán Lớp 3

### 1. GIỚI THIỆU
- Đề tài này giúp tạo tự động đề thi trắc nghiệm, làm bài thi, và thống kê, xuất các kết quả kèm theo.
- Nhóm sử dụng các công cụ: JavaFX tạo mã nguồn, Scene Builder thiết kế giao diện, kết hợp thư viện JDBC kết nối cơ sở dữ liệu ,JDK, Jasper Report xuất báo cáo dạng PDF, SQL server lưu trữ dữ liệu.
- Kết quả đạt được: Phân quyền 2 kiểu người:
    - Giáo viên: Tạo, xóa, sửa đề thi; Thêm, xóa, sửa Ngân hàng câu hỏi, xem báo cáo, xuất đề thi dạng PDF.
    - Học sinh: Thi và xem kết quả bài thi, quyền sửa mật khẩu, nickname.
Cam kết: Đề tài do nhóm tự phân tích thiết kế và không dựa trên đề tài nào khác.

### 2. MÔ TẢ CƠ SỞ DỮ LIỆU
Cơ sở dữ liệu (CSDL) trong hệ thống Kiểm Tra trắc nghiệm của nhóm gồm 8 thực thể (Entity) sau: Người dùng, Học kỳ, Chương, Đề thi, Ngân hàng câu hỏi, Ngân hàng câu trả lời, Chi tiết đề thi, Kết quả thi.
![image](https://github.com/user-attachments/assets/88b7a208-8408-4c78-b61a-51073ab3b505)

### 3. GIAO DIỆN ỨNG DỤNG
- Sau khi đang nhập, hệ thống sẽ tự kiểm tra quyền của User để xác định họ là giáo viên hay học sinh và chuyển sang giao diện
#### 3.1. Admin (Giáo viên): Có 3 trang giao diện:
- Tạo đề thi mới: Tạo mã đề, tên đề, thời gian làm bài và chọn số lượng câu hỏi theo yêu cầu bằng cách chọn số lượng câu hỏi từng chương.
- Điều chỉnh đề thi: Tên đề thi, thời gian làm bài Hoặc Xóa các đề thi mà học sinh chưa làm bài.
- Điều chỉnh ngân hàng câu hỏi: Thêm, Xóa, Sửa câu hỏi trong ngân hàng đề, hỗ trợ lọc câu hỏi theo học kỳ và chương.

- ![image](https://github.com/user-attachments/assets/b329d02d-d9b5-40f4-aec1-f7733b59536a) ![image](https://github.com/user-attachments/assets/54f77820-eb0f-4c3d-8b8f-9f5e0ff13ec7) ![image](https://github.com/user-attachments/assets/fd0ef353-5626-48c8-b7c8-66bc631efaba)



#### 3.2. User (Học sinh): Có 3 trang giao diện:
- Chọn đề muốn thi: chọn 1 trong những đề đã được giáo viên tạo từ trước.
- Tiến hành thi: Chọn các đáp án đúng
- Xem kết quả thi.
- ![image](https://github.com/user-attachments/assets/6c2fc495-908c-4f0f-b126-9ea9829e4b05) ![image](https://github.com/user-attachments/assets/9ca67b86-45b5-4843-9c7f-904989715f15) ![image](https://github.com/user-attachments/assets/7b8c4ea2-98cd-4c50-8d05-696cafff1454)


  
