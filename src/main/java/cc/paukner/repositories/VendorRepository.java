package cc.paukner.repositories;

import cc.paukner.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    Vendor findByName(String name);
}
