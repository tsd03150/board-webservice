package com.kaveloper.portfolio.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUploadFile is a Querydsl query type for UploadFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUploadFile extends EntityPathBase<UploadFile> {

    private static final long serialVersionUID = -633619652L;

    public static final QUploadFile uploadFile = new QUploadFile("uploadFile");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Long> bid = createNumber("bid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modeDate = _super.modeDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final StringPath storeFileName = createString("storeFileName");

    public final StringPath uploadFileName = createString("uploadFileName");

    public QUploadFile(String variable) {
        super(UploadFile.class, forVariable(variable));
    }

    public QUploadFile(Path<? extends UploadFile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUploadFile(PathMetadata metadata) {
        super(UploadFile.class, metadata);
    }

}

