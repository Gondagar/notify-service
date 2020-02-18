package club.renthash.notify.model;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class PendingBlock implements Comparable<PendingBlock> {

    private float id;
    private long blockNumber;
    private String nonce;
    private String coin;
    private String coinIconUrl;
    private String mining;
    private String miningUniq;
    private String reward;
    private String status;
    private String created;
    private String worker;
    private String workerUniq;

    @Override
    public int compareTo(@NotNull PendingBlock o) {
        if (this.blockNumber > o.blockNumber) {
            return -1;
        } else if (this.blockNumber < blockNumber) {
            return 1;
        } else {
            return 0;
        }
    }


    public String toString() {
        return "BlockNumber=" + this.getBlockNumber() + ", nonce=" + this.getNonce() + ", coin=" + this.getCoin() + ", reward=" + this.getReward() + ", status=" + this.getStatus() + ", created=" + this.getCreated() + ", worker=" + this.getWorker() +")";
    }
}