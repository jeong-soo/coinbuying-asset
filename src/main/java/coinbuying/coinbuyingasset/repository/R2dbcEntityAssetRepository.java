package coinbuying.coinbuyingasset.repository;


import coinbuying.coinbuyingasset.entity.UserAsset;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.data.relational.core.query.Criteria.where;

@RequiredArgsConstructor
@Component
public class R2dbcEntityAssetRepository {

    private final R2dbcEntityTemplate template;

    public Mono<UserAsset> findMaxInsertDateByUserId(int userId){
        return this.template.select(UserAsset.class)
                .from("user_asset")
                .matching(Query.query(where("user_id").is(userId))
                        .sort(by(asc("insert_dttm")))).first();

    }

/*
    public Flux<Post> findByBoardTypeAndPostTypeOrderByPostId(BoardType boardType, PostType postType, int offset, int limit){
        return this.template.select(Post.class)
                .from("post")
                .matching(Query.query(where("show_yn").is("Y").and(where("board_type").is(boardType)).and(where("post_type").is(postType)))
                        .sort(by(asc("post_id"))).limit(limit).offset(offset))
                .all();


    }*/

}
