package dev.rahmatullin.repositories.impl;

import dev.rahmatullin.dto.PostDto;
import dev.rahmatullin.models.Post;
import dev.rahmatullin.repositories.PostRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostRepositoryJdbcImpl implements PostRepository {
    private Connection connection;
    private DataSource dataSource;

    private static final String SQL_SELECT_ALL_FROM_POST = "SELECT * FROM post";
    private static final String SQL_INSERT_TO_POST = "INSERT INTO post (text, user_id, title) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE_POST = "UPDATE post SET text = ?, user_id = ?, title = ? WHERE id = ?";
    private static final String SQL_DELETE_POST = "DELETE FROM post WHERE id = ?";
    private static final String SQL_ADD_TO_POST_IMAGE = "INSERT INTO post_image (post_id, image_id) VALUES (?, ?)";

    public PostRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Post> findById(Long id) throws SQLException {
        connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_FROM_POST + " WHERE id = ?");
        statement.setLong(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return Optional.of(new Post(resultSet.getLong("id"),
                    resultSet.getString("text"),
                    resultSet.getString("title"),
                    resultSet.getLong("user_id"),
                    resultSet.getDate("date")));
        }

        return Optional.empty();
    }

    @Override
    public void save(Post entity) throws SQLException {
        connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_INSERT_TO_POST);

        statement.setString(1, entity.getText());
        statement.setLong(2, entity.getUserId());
        statement.setString(3, entity.getTitle());

        statement.executeUpdate();
    }

    @Override
    public void save(List<Post> entities, int countOfEntities) throws SQLException {
        for (int i = 0; i < countOfEntities; i++) {
            save(entities.get(i));
        }
    }

    @Override
    public void update(Post entity) throws SQLException {
        connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_POST);

        statement.setString(1, entity.getText());
        statement.setLong(2, entity.getUserId());
        statement.setString(3, entity.getTitle());
        statement.setLong(4, entity.getId());

        statement.executeUpdate();
    }

    @Override
    public void remove(Post entity) throws SQLException {
        removeById(entity.getId());
    }

    @Override
    public void removeById(Long id) throws SQLException {
        connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_POST);
        statement.setLong(1, id);
        statement.executeUpdate();
    }

    @Override
    public List<PostDto> getPostsWithUserId(Long userId) throws SQLException {
        connection = dataSource.getConnection();
        List<PostDto> posts = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_FROM_POST + " WHERE user_id = ?");
        statement.setLong(1, userId);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
           posts.add(PostDto.builder()
                   .id(resultSet.getLong("id"))
                   .text(resultSet.getString("text"))
                   .title(resultSet.getString("title"))
                   .userId(resultSet.getLong("user_id"))
                   .date(resultSet.getDate("date"))
                   .build()
           );
        }

        return posts;
    }

    @Override
    public void addImageToPost(Long postId, Long imageId) throws SQLException {
        connection = dataSource.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_ADD_TO_POST_IMAGE)) {
            stmt.setLong(1, postId);
            stmt.setLong(2, imageId);
            stmt.executeUpdate();
        }
    }

    @Override
    public void addLike(Long postId, Long likeId) throws SQLException {
        connection = dataSource.getConnection();
        String sql = "INSERT INTO post_like (post_id, like_id) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, postId);
        statement.setLong(2, likeId);
        statement.executeUpdate();
    }

    @Override
    public void removeLike(Long postId) throws SQLException {
        String sql = "DELETE FROM post_like WHERE post_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, postId);
            statement.executeUpdate();
        }
    }

    @Override
    public int getLikeCount(Long postId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM post_like WHERE post_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, postId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }
        return 0;
    }

    @Override
    public List<String> getImagesForPost(long postId) throws SQLException {
        connection = dataSource.getConnection();
        String sql = "SELECT url FROM image i JOIN post_image pi ON i.id = pi.image_id WHERE pi.post_id = ?";
        List<String> imageUrls = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, postId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    imageUrls.add(resultSet.getString("url"));
                }
            }
        }
        return imageUrls;
    }
}