package club.renthash.notify.util;


import club.renthash.notify.model.Block;
import club.renthash.notify.model.PendingBlock;
import club.renthash.notify.model.exception.UpdateCoinStatusException;
import club.renthash.notify.repository.BlockReposotory;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.TreeSet;

@Service
public class NotifyService implements Runnable {

    public static final String telegramUrl = "https://api.telegram.org/bot1041629062:AAEKzqu9NDSi43WE08AlKisceLQMFYk-e5c/sendMessage?";
    static private final OkHttpClient httpClient = new OkHttpClient();
    static ObjectMapper mapper = new ObjectMapper();

  //  @Value("${app.stealer.admin.telegram.chat.id:181426062}")
    String adminChatId;// = "181426062";

  //  @Value("${app.debug:false}")
    boolean isDebugMode;// = true;

  //  @Value("${app.comining.api.key:X5coijZLqzA88X93NdoihHZ}")
    String cominingApiKey;// = "X5coijZLqzA88X93NdoihHZ";

    BigDecimal convertValue = new BigDecimal(100000000);
    Block block;


  //  @Autowired
    BlockReposotory blockReposotory;

    public NotifyService() {
    }


    public NotifyService(Block block, String adminChatId, boolean isDebugMode, String cominingApiKey, BlockReposotory blockReposotory) {
        this.block = block;
        this.adminChatId = adminChatId;
        this.isDebugMode = isDebugMode;
        this.cominingApiKey = cominingApiKey;
        this.blockReposotory = blockReposotory;

    }


    public static void adminSubmitBlockNotify(Block block) {
        try {

            String smile = "\uD83D\uDE04";

            String url = String.format(telegramUrl + "chat_id=181426062&text=[%s] Submitted new block %d %s. Difficulty: share/network  %s/%s. Miner %s with IP %s.",
                    block.getCoin(), block.getBlockNumber(), smile, HashRateUtil.getPrettyDiff(block.getShareDiff()),
                    HashRateUtil.getPrettyDiff(block.getNetworkDiff()), block.getRigName(), block.getIp());
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = httpClient.newCall(request).execute();
            response.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void adminConfirmBlockNotify(Block block) {
        try {

            String smile = "\uD83D\uDE04";

            String url = String.format(telegramUrl + "chat_id=181426062&text=[%s] Found and confirmed new block %d %s. Difficulty: share/network  %s/%s. Reward %4.8f. Miner %s with IP %s.",
                    block.getCoin(), block.getBlockNumber(), smile, HashRateUtil.getPrettyDiff(block.getShareDiff()),
                    HashRateUtil.getPrettyDiff(block.getNetworkDiff()), block.getReward(), block.getRigName(), block.getIp());
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = httpClient.newCall(request).execute();
            response.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void blockNotify(Block block) {

        //send to admin account all notification
        adminConfirmBlockNotify(block);

        String chatId = block.getChatId();

        if (!adminChatId.equals(chatId)) {
            try {
                String smile = "\uD83D\uDE04";
                //send to miner personal notification
                if (chatId != null) {
                    String url = String.format(telegramUrl + "chat_id=%s&text=[%s] " +
                                    "Found and confirmed new block %d %s. Difficulty: share/network  %s/%s. Reward %4.8f. Miner %s with IP %s.",
                            chatId, block.getCoin(), block.getBlockNumber(), smile, HashRateUtil.getPrettyDiff(block.getShareDiff()),
                            HashRateUtil.getPrettyDiff(block.getNetworkDiff()), block.getReward(), block.getRigName(), block.getIp());
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Response response = httpClient.newCall(request).execute();
                    response.close();
                } else {
                    String url = String.format(telegramUrl + "chat_id=181426062&text= [%s] System can't sent new block %d notifying. Account chat ID not found", block.getCoin(), block.getBlockNumber());
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Response response = httpClient.newCall(request).execute();
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //Ми уже себе сповістили як адміна)
        }
    }

    public static void adminBlockDonateNotify(Block block) {
        try {

            String smile = "\uD83D\uDE12";
            String url = String.format(telegramUrl + "chat_id=181426062&text=[%s] New block %d donated to pool %s. Difficulty: share/network  %s/%s. Miner %s with IP %s.", block.getCoin().toUpperCase(), block.getBlockNumber(), smile, HashRateUtil.getPrettyDiff(block.getShareDiff()), HashRateUtil.getPrettyDiff(block.getNetworkDiff()), block.getRigName(), block.getIp());


            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = httpClient.newCall(request).execute();
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void blockDonateNotify(Block block) {
        //send to admin account all notification
        adminBlockDonateNotify(block);

        String chatId = block.getChatId();

        if (!adminChatId.equals(chatId)) {
            try {
                String smile = "\uD83D\uDE04";
                //send to miner personal notification
                if (chatId != null) {
                    String url = String.format(telegramUrl + "chat_id=%s&text=[%s] New block %d donated to pool %s. Difficulty: share/network  %s/%s. Miner %s with IP %s.", chatId, block.getCoin().toUpperCase(), block.getBlockNumber(), smile, HashRateUtil.getPrettyDiff(block.getShareDiff()), HashRateUtil.getPrettyDiff(block.getNetworkDiff()), block.getRigName(), block.getIp());

                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Response response = httpClient.newCall(request).execute();
                    response.close();
                } else {
                    String url = String.format(telegramUrl + "chat_id=181426062&text= [%s] System can't sent new block %d notifying. Account chat ID not found", block.getCoin().toUpperCase(), block.getBlockNumber());
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Response response = httpClient.newCall(request).execute();
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //Ми уже себе сповістили як адміна)
        }

    }


    public static void adminBlockRejectedNotify(Block block) {
        try {

            String smile = "\uD83D\uDE12";
            String url = String.format(telegramUrl + "chat_id=181426062&text=[%s] Miner %s with IP %s send block solution but it was rejected. Status %s %s. Difficulty: share/network  %s/%s.", block.getCoin(), block.getRigName(), block.getIp(), block.getStatus(), smile, HashRateUtil.getPrettyDiff(block.getShareDiff()), HashRateUtil.getPrettyDiff(block.getNetworkDiff()));


            Request request = new Request.Builder().url(url)
                    .build();
            Response response = httpClient.newCall(request).execute();
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void blockRejectedNotify(Block block) {

        adminBlockRejectedNotify(block);
        try {

            String chatId = block.getChatId();
            String smile = "\uD83D\uDE12";
            String url = String.format(telegramUrl + "chat_id=%s&text=[%s] Miner %s with IP %s send block solution but it was rejected. Status %s %s. Difficulty: share/network  %s/%s.", chatId, block.getCoin(), block.getRigName(), block.getIp(), block.getStatus(), smile, HashRateUtil.getPrettyDiff(block.getShareDiff()), HashRateUtil.getPrettyDiff(block.getNetworkDiff()));


            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = httpClient.newCall(request).execute();
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    @Transient
    public void run() {
        String lastStatus = "";
        long currentBlock = 0;
        long blockIdInDB = 0;
        try {
            Thread.sleep(6300);

            PendingBlock lastPendingBlock = getLastBlockFromPool().first();

            if (lastPendingBlock != null) {
                block.setBlockNumber(lastPendingBlock.getBlockNumber());
                block.setNonce(lastPendingBlock.getNonce());
                currentBlock = lastPendingBlock.getBlockNumber();
                lastStatus = lastPendingBlock.getStatus();

                System.out.printf("[submit]-> Fetched new information about current block %d [%s]\n", currentBlock, lastPendingBlock);
                blockIdInDB = blockReposotory.save(block).getId();
                adminSubmitBlockNotify(block);

            }

            while (true) {
                try {
                    Thread.sleep(150000);
                    TreeSet<PendingBlock> pendingBlocks = getLastBlockFromPool();

                    for (PendingBlock block : pendingBlocks) {

                        if (block.getBlockNumber() == currentBlock) {
                            lastPendingBlock = block;
                            if (isDebugMode) {
                                System.out.printf("[submit]-> Fetched new information about current block %d [%s]\n", currentBlock, lastPendingBlock);
                            }
                        }
                    }

                    if (lastPendingBlock.getStatus().contains("Network rejected") || lastPendingBlock.getStatus().contains("Node rejected")) {
                        System.out.printf("[submit]-> Submitted %s block %d was rejected. Status [%s] Thread will be stopped now. \n", block.getCoin(), block.getBlockNumber(), lastPendingBlock.getStatus());
                        block.setStatus(lastPendingBlock.getStatus());
                        blockRejectedNotify(block);
                     //   blockReposotory.save(block);
                        break;
                    } else if (lastPendingBlock.getStatus().contains("Pending")) {
                        if (!lastStatus.equals(lastPendingBlock.getStatus())) {
                            try {

                                final Block blockFomBD = blockReposotory.getOne(blockIdInDB);

                                block.setReward(new BigDecimal(lastPendingBlock.getReward()).divide(convertValue, 8, RoundingMode.HALF_UP).doubleValue());
                                block.setStatus(lastPendingBlock.getStatus());
                                System.out.printf("[submit]-> [%-5s] Block %d new status [%s] Reward for block %4.8f\n", block.getCoin(), block.getBlockNumber(), lastPendingBlock.getStatus(), block.getReward());
                                lastStatus = lastPendingBlock.getStatus();
                                blockReposotory.save(blockFomBD);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                    } else if (lastPendingBlock.getStatus().contains("Confirmed")) {


                        block.setStatus(lastPendingBlock.getStatus());
                        block.setReward(new BigDecimal(lastPendingBlock.getReward()).divide(convertValue, 8, RoundingMode.HALF_UP).doubleValue());
                        System.out.printf("[submit]-> [%-5s] block %d successfully confirmed. Added to withdraw list and will be send message to miner over telegram\n", block.getCoin(), block.getBlockNumber(), block.getStatus());

                        blockReposotory.save(block);
                        blockNotify(block);
                        break;
                    }

                } catch (Exception e) {
                    System.out.printf("[error ]-> Помилка  при циклічному отриманні статусу підтверджень блоків  - %s\n", e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (UpdateCoinStatusException ex) {
            System.out.printf("[error ]-> UpdateCoinStatusException %s t\n", ex.getMessage());

            blockNotify(block);

        } catch (InterruptedException e) {
            System.out.printf("[error ]-> %s t\n", e.getMessage());
            e.printStackTrace();
            blockNotify(block);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.printf("[error ]-> Exception %s \n", ex.getMessage());
            blockNotify(block);
        }
    }

    public TreeSet<PendingBlock> getLastBlockFromPool() {

        String url = "https://api.comining.io/?key=" + cominingApiKey;

        TreeSet<PendingBlock> sortedLastBlocks = new TreeSet<>();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String method = "{\"method\":\"blocks_list\"}";
        RequestBody body = RequestBody.create(JSON, method);

        try {

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = httpClient.newCall(request).execute();
            final String json = response.body().string();
            JSONObject obj = new JSONObject(json);
            JSONArray arrayBlock = obj.getJSONArray("data");
            arrayBlock.forEach(rawBlock -> {
                try {
                    final PendingBlock lastBlock = mapper.readValue(arrayBlock.get(0).toString(), PendingBlock.class);
                    sortedLastBlocks.add(lastBlock);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


            return sortedLastBlocks;
        } catch (Exception ex) {
            throw new UpdateCoinStatusException(ex.getMessage());
        }


    }


}
