package com.gard.investmentmanager.asset.infrastructure.rest;

import com.gard.investmentmanager.asset.application.port.in.CreateAssetCommand;
import com.gard.investmentmanager.asset.application.port.in.CreateAssetUC;
import com.gard.investmentmanager.asset.application.port.in.DeleteAssetUC;
import com.gard.investmentmanager.asset.application.port.in.GetAssetByIdUC;
import com.gard.investmentmanager.asset.application.port.in.ListAssetsUC;
import com.gard.investmentmanager.asset.application.port.in.UpdateAssetCommand;
import com.gard.investmentmanager.asset.application.port.in.UpdateAssetUC;
import com.gard.investmentmanager.asset.domain.Asset;
import com.gard.investmentmanager.shared.application.port.in.CurrentUserProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class AssetResource implements AssetResourceContract {

    private final CreateAssetUC createAssetUC;
    private final ListAssetsUC listAssetsUC;
    private final GetAssetByIdUC getAssetByIdUC;
    private final UpdateAssetUC updateAssetUC;
    private final DeleteAssetUC deleteAssetUC;
    private final AssetRestMapper assetRestMapper;
    private final CurrentUserProvider currentUserProvider;

    public AssetResource(
            CreateAssetUC createAssetUC,
            ListAssetsUC listAssetsUC,
            GetAssetByIdUC getAssetByIdUC,
            UpdateAssetUC updateAssetUC,
            DeleteAssetUC deleteAssetUC,
            AssetRestMapper assetRestMapper,
            CurrentUserProvider currentUserProvider
    ) {
        this.createAssetUC = createAssetUC;
        this.listAssetsUC = listAssetsUC;
        this.getAssetByIdUC = getAssetByIdUC;
        this.updateAssetUC = updateAssetUC;
        this.deleteAssetUC = deleteAssetUC;
        this.assetRestMapper = assetRestMapper;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public Response create(CreateAssetRequest request) {
        Long currentUserId = currentUserProvider.getCurrentUser().id();

        Asset created = createAssetUC.execute(
                currentUserId,
                new CreateAssetCommand(
                        request.assetType(),
                        request.name(),
                        request.ticker(),
                        request.country(),
                        request.currency(),
                        request.issuer(),
                        request.metadataJson()
                )
        );

        AssetResponse response = assetRestMapper.toResponse(created);

        return Response.created(URI.create("/api/v1/assets/" + response.id()))
                .entity(response)
                .build();
    }

    @Override
    public List<AssetResponse> listAll() {
        Long currentUserId = currentUserProvider.getCurrentUser().id();
        return assetRestMapper.toResponseList(listAssetsUC.execute(currentUserId));
    }

    @Override
    public AssetResponse getById(Long assetId) {
        Long currentUserId = currentUserProvider.getCurrentUser().id();
        return assetRestMapper.toResponse(getAssetByIdUC.execute(currentUserId, assetId));
    }

    @Override
    public AssetResponse update(Long assetId, UpdateAssetRequest request) {
        Long currentUserId = currentUserProvider.getCurrentUser().id();

        return assetRestMapper.toResponse(
                updateAssetUC.execute(
                        currentUserId,
                        assetId,
                        new UpdateAssetCommand(
                                request.assetType(),
                                request.name(),
                                request.ticker(),
                                request.country(),
                                request.currency(),
                                request.issuer(),
                                request.metadataJson()
                        )
                )
        );
    }

    @Override
    public Response delete(Long assetId) {
        Long currentUserId = currentUserProvider.getCurrentUser().id();
        deleteAssetUC.execute(currentUserId, assetId);
        return Response.noContent().build();
    }
}