package club.renthash.notify.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Block {

    @Id
    @GeneratedValue
    Long id;

    private LocalDateTime date;

    private String coin;


    @Column(name = "block_number")
    private Long blockNumber;

    @Column(name = "network_diff")
    private Long networkDiff;

    @Column(name = "share_diff")
    private Long shareDiff;

    String nonce;

    private String rigName;

    private String ip;

    private String chatId;

    private Double reward;

    String status;

    @Column(name = "is_unpaid")
    boolean isUnpaid;


}

