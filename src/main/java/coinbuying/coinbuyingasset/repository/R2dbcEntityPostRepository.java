package coinbuying.coinbuyingasset.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class R2dbcEntityPostRepository {

    private final R2dbcEntityTemplate template;

    /*public Flux<Post> findFavorites(int offset, int limit){
        return this.template.select(Post.class)
                .from("post")
                .matching(Query.query(where("counter").notIn(0).and(where("show_yn").is("Y")))
                        .sort(by(desc("counter"))).limit(limit).offset(offset))
                        .all();


    }

    public Flux<Post> findByBoardTypeOrderByPostId(BoardType boardType, int offset, int limit){
        return this.template.select(Post.class)
                .from("post")
                .matching(Query.query(where("show_yn").is("Y").and(where("board_type").is(boardType)))
                        .sort(by(asc("post_id"))).limit(limit).offset(offset))
                .all();

    }


    public Flux<Post> findByBoardTypeAndPostTypeOrderByPostId(BoardType boardType, PostType postType, int offset, int limit){
        return this.template.select(Post.class)
                .from("post")
                .matching(Query.query(where("show_yn").is("Y").and(where("board_type").is(boardType)).and(where("post_type").is(postType)))
                        .sort(by(asc("post_id"))).limit(limit).offset(offset))
                .all();


    }*/

}
