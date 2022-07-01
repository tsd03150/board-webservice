package com.kaveloper.portfolio.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReplyComment is a Querydsl query type for ReplyComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReplyComment extends EntityPathBase<ReplyComment> {

    private static final long serialVersionUID = 55040116L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReplyComment replyComment = new QReplyComment("replyComment");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QBoard board;

    public final NumberPath<Long> cid = createNumber("cid", Long.class);

    public final QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modeDate = _super.modeDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final QReply reply;

    public final StringPath text = createString("text");

    public QReplyComment(String variable) {
        this(ReplyComment.class, forVariable(variable), INITS);
    }

    public QReplyComment(Path<? extends ReplyComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReplyComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReplyComment(PathMetadata metadata, PathInits inits) {
        this(ReplyComment.class, metadata, inits);
    }

    public QReplyComment(Class<? extends ReplyComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.reply = inits.isInitialized("reply") ? new QReply(forProperty("reply"), inits.get("reply")) : null;
    }

}

