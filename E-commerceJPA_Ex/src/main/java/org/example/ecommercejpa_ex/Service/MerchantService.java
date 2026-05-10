package org.example.ecommercejpa_ex.Service;
import lombok.RequiredArgsConstructor;
import org.example.ecommercejpa_ex.Model.Merchant;
import org.example.ecommercejpa_ex.Repository.MerchantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService {
    private final MerchantRepository merchantRepository;


        public List<Merchant> get() {
            return merchantRepository.findAll();
        }

        public void add(Merchant merchant) {
            merchantRepository.save(merchant);
        }

        public Boolean update(Integer id, Merchant merchant) {
            Merchant oldMerchant = merchantRepository.findById(id).orElse(null);

            if (oldMerchant == null) {
                return false;
            }

            oldMerchant.setName(merchant.getName());
            merchantRepository.save(oldMerchant);
            return true;
        }

        public Boolean delete(Integer id) {
            Merchant merchant = merchantRepository.findById(id).orElse(null);

            if (merchant == null) {
                return false;
            }

            merchantRepository.delete(merchant);
            return true;
        }
    }



