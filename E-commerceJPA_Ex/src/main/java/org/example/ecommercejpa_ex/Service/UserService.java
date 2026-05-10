package org.example.ecommercejpa_ex.Service;
import lombok.RequiredArgsConstructor;
import org.example.ecommercejpa_ex.Model.MerchantStock;
import org.example.ecommercejpa_ex.Model.Product;
import org.example.ecommercejpa_ex.Model.User;
import org.example.ecommercejpa_ex.Repository.MerchantStockRepository;
import org.example.ecommercejpa_ex.Repository.ProductRepository;
import org.example.ecommercejpa_ex.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final MerchantStockRepository merchantStockRepository;

        public List<User> get() {
            return userRepository.findAll();
        }

        public void add(User user) {
            userRepository.save(user);
        }

        public Boolean update(Integer id, User user) {
            User oldUser = userRepository.findById(id).orElse(null);
            if (oldUser == null) return false;

            oldUser.setUserName(user.getUserName());
            oldUser.setPassword(user.getPassword());
            oldUser.setEmail(user.getEmail());
            oldUser.setRole(user.getRole());
            oldUser.setBalance(user.getBalance());

            userRepository.save(oldUser);
            return true;
        }

        public Boolean delete(Integer id) {
            User user = userRepository.findById(id).orElse(null);
            if (user == null) return false;

            userRepository.delete(user);
            return true;
        }
    // Get Users by Role باستخدام الفور العادية
    public List<User> getUsersByRole(String role) {
        // 1. نجلب كل المستخدمين من قاعدة البيانات
        List<User> allUsers = userRepository.findAll();

        // 2. ننشئ قائمة جديدة لتخزين المستخدمين المطابقين للرول
        List<User> filteredUsers = new ArrayList<>();

        // 3. نمر على القائمة كاملة باستخدام الفور التقليدية
        for (int i = 0; i < allUsers.size(); i++) {
            // نتحقق إذا كان الرول مطابق (بدون التحسس لحالة الأحرف)
            if (allUsers.get(i).getRole().equalsIgnoreCase(role)) {
                filteredUsers.add(allUsers.get(i));
            }
        }

        return filteredUsers;
    }


    public int buyProduct(Integer userId, Integer productId, Integer merchantId) {
        List<User> users = userRepository.findAll();
        List<Product> products = productRepository.findAll();
        List<MerchantStock> stocks = merchantStockRepository.findAll();

        User currentUser = null;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(userId)) {
                currentUser = users.get(i);
                break;
            }
        }
        if (currentUser == null) return -1;

        Product currentProduct = null;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(productId)) {
                currentProduct = products.get(i);
                break;
            }
        }
        if (currentProduct == null) return -2;

        MerchantStock currentStock = null;
        for (int i = 0; i < stocks.size(); i++) {
            if (stocks.get(i).getMerchantId().equals(merchantId) &&
                    stocks.get(i).getProductId().equals(productId)) {
                currentStock = stocks.get(i);
                break;
            }
        }
        if (currentStock == null) return -3;
        if (currentStock.getStock() <= 0) return -4;
        if (currentUser.getBalance() < currentProduct.getPrice()) return -5;

        // تحديث البيانات
        currentUser.setBalance(currentUser.getBalance() - currentProduct.getPrice());
        currentUser.setTotalSpent(currentUser.getTotalSpent() + currentProduct.getPrice());
        currentStock.setStock(currentStock.getStock() - 1);

        if (currentUser.getTotalSpent() >= 5000) currentUser.setIsVip(true);

        userRepository.save(currentUser);
        merchantStockRepository.save(currentStock);
        return 1;
    }

    //Refund Product
    public int returnProduct(Integer userId, Integer productId, Integer merchantId) {
        List<User> users = userRepository.findAll();
        List<Product> products = productRepository.findAll();
        List<MerchantStock> stocks = merchantStockRepository.findAll();

        User u = null;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(userId)) {
                u = users.get(i);
                break;
            }
        }

        Product p = null;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(productId)) {
                p = products.get(i);
                break;
            }
        }

        MerchantStock ms = null;
        for (int i = 0; i < stocks.size(); i++) {
            if (stocks.get(i).getMerchantId().equals(merchantId) &&
                    stocks.get(i).getProductId().equals(productId)) {
                ms = stocks.get(i);
                break;
            }
        }

        if (u == null || p == null || ms == null) return -1;

        u.setBalance(u.getBalance() + p.getPrice());
        u.setTotalSpent(u.getTotalSpent() - p.getPrice());
        ms.setStock(ms.getStock() + 1);

        if (u.getTotalSpent() < 5000) u.setIsVip(false);

        userRepository.save(u);
        merchantStockRepository.save(ms);
        return 1;
    }

    // VIP & Coupon
    public String generateCoupon(Integer userId) {
        List<User> users = userRepository.findAll();

        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            if (u.getId().equals(userId)) {
                if (u.getTotalSpent() > 5000) {
                    u.setIsVip(true);
                    userRepository.save(u);
                    int randomNum = (int) (Math.random() * 999);
                    return u.getUserName() + u.getTotalSpent() + randomNum;
                }
            }
        }
        return "No coupon available";
    }}





