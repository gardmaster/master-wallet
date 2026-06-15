package com.gard.investmentmanager.asset.infrastructure.rest;

import com.gard.investmentmanager.asset.application.port.in.CreateAssetCommand;
import com.gard.investmentmanager.asset.application.port.in.CreateAssetUC;
import com.gard.investmentmanager.asset.application.port.in.DeleteAssetUC;
import com.gard.investmentmanager.asset.application.port.in.GetAssetByIdUC;
import com.gard.investmentmanager.asset.application.port.in.ListAssetsUC;
import com.gard.investmentmanager.asset.application.port.in.UpdateAssetCommand;
import com.gard.investmentmanager.asset.application.port.in.UpdateAssetUC;
import com.gard.investmentmanager.asset.domain.Asset;
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

    public AssetResource(
            CreateAssetUC createAssetUC,
            ListAssetsUC listAssetsUC,
            GetAssetByIdUC getAssetByIdUC,
            UpdateAssetUC updateAssetUC,
            DeleteAssetUC deleteAssetUC,
            AssetRestMapper assetRestMapper
    ) {
        this.createAssetUC = createAssetUC;
        this.listAssetsUC = listAssetsUC;
        this.getAssetByIdUC = getAssetByIdUC;
        this.updateAssetUC = updateAssetUC;
        this.deleteAssetUC = deleteAssetUC;
        this.assetRestMapper = assetRestMapper;
    }

    @Override
    public Response create(CreateAssetRequest request) {
        Asset created = createAssetUC.execute(
                new CreateAssetCommand(
                        request.userId(),
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
        return assetRestMapper.toResponseList(listAssetsUC.execute());
    }

    @Override
    public AssetResponse getById(Long assetId) {
        return assetRestMapper.toResponse(getAssetByIdUC.execute(assetId));
    }

    @Override
    public AssetResponse update(Long assetId, UpdateAssetRequest request) {
        return assetRestMapper.toResponse(
                updateAssetUC.execute(
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
        deleteAssetUC.execute(assetId);
        return Response.noContent().build();
    }
}