package com.example.boardservice.application;

import com.example.boardservice.application.port.in.BuyAndSellUseCase;
import com.example.boardservice.application.port.out.BuyAndSellCommandPort;
import com.example.boardservice.application.port.out.BuyAndSellQueryPort;
import com.example.boardservice.domain.BuyAndSell;
import com.example.boardservice.security.PassportHolder;
import com.example.common.exception.UnauthorizedException;
import com.example.imagemodule.application.port.ImageCommand;
import com.example.imagemodule.application.port.SaveImagesCommand;
import com.example.imagemodule.domain.MinioBucket;
import com.example.imagemodule.util.ObjectUrlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

import static com.example.boardservice.domain.ArticleType.BUY_AND_SELL;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuyAndSellService implements BuyAndSellUseCase {

    private final ImageCommand imageCommand;
    private final BuyAndSellCommandPort buyAndSellCommandPort;
    private final BuyAndSellQueryPort buyAndSellQueryPort;
    private final ObjectUrlMapper objectUrlMapper;

    @Override
    @Transactional
    public Long postBuyAndSell(BuyAndSell buyAndSell, List<MultipartFile> imageFiles) {
        // Todo 이미지 순서 관련 기획 논의
        // Todo 임시저장 관련 기획
        buyAndSell.updateImageUrls(getImageUrls(imageFiles));
        return buyAndSellCommandPort.save(buyAndSell).getArticleId();
    }


    @Override
    @Transactional
    public Long updateBuyAndSell(BuyAndSell buyAndSell, List<MultipartFile> imageFiles) {
        BuyAndSell originBuyAndSell = buyAndSellQueryPort.getBuyAndSell(buyAndSell.getArticleId());

        validatePermission(originBuyAndSell.getMemberId(), buyAndSell.getMemberId());

        if(!CollectionUtils.isEmpty(imageFiles)) {
            buyAndSell.updateImageUrls(getImageUrls(imageFiles));
        } else {
            buyAndSell.updateImageUrls(originBuyAndSell.getImageUrls());
        }

        buyAndSellCommandPort.save(buyAndSell);
        return buyAndSell.getArticleId();
    }



    @Override
    @Transactional
    public Long delete(BuyAndSell buyAndSell) {
        BuyAndSell originBuyAndSell = buyAndSellQueryPort.getBuyAndSell(buyAndSell.getArticleId());
        validatePermission(originBuyAndSell.getMemberId(), buyAndSell.getMemberId());

        buyAndSellCommandPort.delete(originBuyAndSell);
        return buyAndSell.getArticleId();
    }

    @Override
    public Page<BuyAndSell> getBuyAndSellList(int page) {
        return buyAndSellQueryPort.getBuyAndSellList(page);
    }

    @Override
    @Transactional
    public BuyAndSell getBuyAndSell(Long id) {
        BuyAndSell buyAndSell = buyAndSellQueryPort.getBuyAndSell(id);
        buyAndSell.incrementViewCount(PassportHolder.getPassport().memberId());
        buyAndSellCommandPort.save(buyAndSell);
        return buyAndSell;
    }

    @Override
    public Page<BuyAndSell> getBuyAndSellListByMemberId(Long memberId, Pageable pageable) {
        return buyAndSellQueryPort.getBuyAndSellListByMemberId(memberId, pageable);
    }


    private List<String> getImageUrls (List<MultipartFile> imageFiles) {
        List<String> objectNames = imageCommand.saveImages(
                new SaveImagesCommand(imageFiles, BUY_AND_SELL.getBucket())
        );
        return objectUrlMapper.toUrl(objectNames, MinioBucket.ARTICLE_TRADE);
    }


    private void validatePermission(final Long originMemberId, final Long memberId) {
        if(!Objects.equals(originMemberId, memberId)) {
            throw new UnauthorizedException("글을 수정하거나 삭제할 권한이 없습니다.");
        }
    }
}
