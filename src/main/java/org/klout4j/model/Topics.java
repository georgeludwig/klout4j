package org.klout4j.model;

import org.json.simple.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A class representing the topics over which a user has influence.
 *
 * @author George
 */
public class Topics implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Topic> topics;

    @SuppressWarnings("unchecked")
    public Topics(JSONArray attributes) {
        topics = new ArrayList<Topic>();
        if (attributes != null && !attributes.isEmpty()) {
            for (Object attribute : attributes) {
                topics.add(new Topic((Map<String, Object>) attribute));
            }
        }
    }

    public List<Topic> getTopics() {
        if (topics == null) {
            topics = new ArrayList<Topic>();
        }
        return topics;
    }

    public static class Topic implements Serializable {
        private Long id;
        private String displayName;
        private String name;
        private String slug;
        private String imageUrl;
        private String topicType;

        public Topic(Map<String, Object> attributes) {
            if (attributes.containsKey("id")) {
                id = Long.parseLong(attributes.get("id").toString());
            }
            displayName = (String) attributes.get("displayName");
            name = (String) attributes.get("name");
            slug = (String) attributes.get("slug");
            imageUrl = (String) attributes.get("imageUrl");
            topicType = (String) attributes.get("topicType");
        }

        public Long getId() {
            return id;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getSlug() {
            return slug;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getTopicType() {
            return topicType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Topic topic = (Topic) o;

            if (id != null ? !id.equals(topic.id) : topic.id != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return id != null ? id.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "Topic{" +
                    "id=" + id +
                    ", displayName='" + displayName + '\'' +
                    ", slug='" + slug + '\'' +
                    ", imageUrl='" + imageUrl + '\'' +
                    ", topicType='" + topicType + '\'' +
                    '}';
        }
    }

}
