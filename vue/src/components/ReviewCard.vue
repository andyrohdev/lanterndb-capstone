<template>
  <div class="review-card">
    <h3 class="review-title">{{ review.review_title }}</h3>
    <p class="game-title">Game: {{ gameTitle }}</p>
    <p class="review-text">{{ review.review_text }}</p>
    <p class="review-username">Reviewed by: {{ username }}</p>

    <div v-if="isReviewAuthor" class="review-actions">
      <button @click="toggleEditModal" class="btn btn-secondary">Edit</button>
      <button @click="removeReview" class="btn btn-danger">Delete</button>
    </div>

    <!-- Comment Section -->
    <div class="comment-section">
      <button @click="toggleComments" class="btn btn-primary">
        {{ showComments ? 'Hide Comments' : 'Show Comments' }}
      </button>

      <div v-if="showComments" class="comments-container">
        <div v-for="comment in comments" :key="comment.id" class="comment">
          <p v-if="!comment.editing" class="comment-text">{{ comment.text }}</p>
          <p class="comment-author">Comment by: {{ comment.author }}</p>

          <!-- Comment Edit Form -->
          <div v-if="comment.editing" class="edit-comment-form">
            <textarea
              v-model="comment.editedText"
              placeholder="Edit your comment"
              rows="3"
            ></textarea>
            <button @click="submitCommentEdit(comment)" class="btn btn-primary">
              Save
            </button>
            <button @click="cancelCommentEdit(comment)" class="btn btn-secondary">
              Cancel
            </button>
          </div>

          <div v-if="isCommentAuthor(comment)" class="comment-actions">
            <button @click="toggleCommentEdit(comment)" class="btn btn-secondary">Edit</button>
            <button @click="deleteComment(comment.id)" class="btn btn-danger">Delete</button>
          </div>
        </div>

        <div v-if="isUserLoggedIn" class="add-comment-section">
          <button @click="toggleAddCommentForm" class="btn btn-secondary">
            Add Comment
          </button>

          <div v-if="showAddCommentForm" class="add-comment-form">
            <textarea
              v-model="newCommentText"
              placeholder="Write your comment here."
              rows="3"
            ></textarea>
            <button @click="submitComment" class="btn btn-primary">
              Submit
            </button>
            <button @click="toggleAddCommentForm" class="btn btn-secondary">
              Cancel
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Edit Review Modal -->
    <div v-if="editModalVisible" class="edit-modal">
      <div class="review-form-content">
        <h3>Edit Your Review</h3>
        <input
          v-model="editTitle"
          placeholder="Review Title"
          class="review-title-input"
        />
        <textarea
          v-model="editText"
          placeholder="Write your review here."
          rows="4"
        ></textarea>
        <button @click="submitEdit" class="btn btn-primary">
          Save Changes
        </button>
        <button @click="toggleEditModal" class="btn btn-secondary">
          Cancel
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex';
import GameService from "../services/GameService";

export default {
  props: {
    review: Object,
  },
  data() {
    return {
      username: "Loading...",
      gameTitle: "Loading...",
      editModalVisible: false,
      editTitle: "",
      editText: "",
      showComments: false,
      comments: [],
      showAddCommentForm: false,
      newCommentText: "",
    };
  },
  computed: {
    ...mapState(['user', 'token']),
    isReviewAuthor() {
      return this.review.user_id === this.user.id;
    },
    isUserLoggedIn() {
      return !!this.token;
    },
  },
  mounted() {
    this.fetchUsername(this.review.user_id);
    this.fetchGameTitle(this.review.game_id);
    this.resetEditForm();
  },
  methods: {
    ...mapActions(['updateReview', 'deleteReview']),
    fetchUsername(userId) {
      GameService.fetchUsers()
        .then((response) => {
          const user = response.data.find((user) => user.id === userId);
          this.username = user ? user.username : "Anonymous";
        })
        .catch((error) => {
          console.error("Error fetching username:", error);
          this.username = "Anonymous";
        });
    },
    fetchGameTitle(gameId) {
      GameService.getGameDetails(gameId)
        .then((response) => {
          this.gameTitle = response.data.name || "Unknown Game";
        })
        .catch((error) => {
          console.error("Error fetching game title:", error);
          this.gameTitle = "Unknown Game";
        });
    },
    toggleEditModal() {
      this.editModalVisible = !this.editModalVisible;
      this.resetEditForm();
    },
    resetEditForm() {
      this.editTitle = this.review.review_title;
      this.editText = this.review.review_text;
    },
    submitEdit() {
      console.log("Submit edit for review:", this.review.review_id);
      const updatedReview = {
        review_id: this.review.review_id,
        game_id: this.review.game_id,
        review_title: this.editTitle,
        review_text: this.editText,
        user_id: this.review.user_id
      };

      this.updateReview(updatedReview)
        .then(() => {
          console.log("Review updated successfully");
          this.editModalVisible = false;
          this.$emit('review-updated');
        })
        .catch(error => {
          console.error("Error updating review:", error);
        });
    },
    removeReview() {
      if (confirm("Are you sure you want to delete this review?")) {
      console.log("Delete review:", this.review.review_id);
      this.deleteReview(this.review.review_id)
        .then(() => {
          console.log("Review deleted successfully");
          this.$emit('review-updated');
        })
        .catch(error => {
          console.error("Error deleting review:", error);
        });
      }
    },
    toggleComments() {
      this.showComments = !this.showComments;
      if (this.showComments) {
        this.fetchComments();
      }
    },
    fetchComments() {
      console.log("Fetching comments for review:", this.review.review_id);
      // Placeholder for fetching comments from API
      // GameService.getComments(this.review.review_id)
      //   .then((response) => {
      //     this.comments = response.data;
      //   })
      //   .catch((error) => {
      //     console.error("Error fetching comments:", error);
      //   });

      // Temporary mock comments
      this.comments = [
        { id: 1, text: "Great review!", author: "User1", user_id: 1 },
        { id: 2, text: "I disagree with your points.", author: "User2", user_id: 2 }
      ];
    },
    isCommentAuthor(comment) {
      return comment.user_id === this.user.id;
    },
    toggleCommentEdit(comment) {
      comment.editing = !comment.editing;
      if (comment.editing) {
        comment.editedText = comment.text;
      }
    },
    submitCommentEdit(comment) {
      console.log("Submitting edit for comment:", comment.id);
      if (!comment.editedText.trim()) {
        alert("Please provide a comment.");
        return;
      }

      // Placeholder for updating comment in the API
      const updatedComment = {
        id: comment.id,
        text: comment.editedText,
        review_id: this.review.review_id,
        user_id: comment.user_id,
      };

      // GameService.updateComment(updatedComment)
      //   .then(() => {
      //     console.log("Comment updated successfully");
      //     comment.text = comment.editedText;
      //     comment.editing = false;
      //   })
      //   .catch((error) => {
      //     console.error("Error updating comment:", error);
      //   });

      // Temporary mock update
      comment.text = comment.editedText;
      comment.editing = false;
    },
    cancelCommentEdit(comment) {
      comment.editing = false;
    },
    deleteComment(commentId) {
      if (confirm("Are you sure you want to delete this comment?")) {
      console.log("Deleting comment:", commentId);
      // Placeholder for delete comment API call
      // GameService.deleteComment(commentId)
      //   .then(() => {
      //     this.fetchComments();
      //   })
      //   .catch((error) => {
      //     console.error("Error deleting comment:", error);
      //   });

      // Temporary mock delete
      this.comments = this.comments.filter(comment => comment.id !== commentId);
      }
    },
    toggleAddCommentForm() {
      this.showAddCommentForm = !this.showAddCommentForm;
    },
    submitComment() {
      console.log("Submitting comment:", this.newCommentText);
      if (!this.newCommentText.trim()) {
        alert("Please provide a comment.");
        return;
      }

      const commentData = {
        review_id: this.review.review_id,
        text: this.newCommentText,
        author: this.user.username,
        user_id: this.user.id,
      };

      // Placeholder for submitting comment to API
      // GameService.addComment(commentData)
      //   .then(() => {
      //     console.log("Comment submitted successfully");
      //     this.fetchComments();
      //     this.showAddCommentForm = false;
      //     this.newCommentText = "";
      //   })
      //   .catch((error) => {
      //     console.error("Error submitting comment:", error);
      //   });

      // Temporary mock comment submission
      this.comments.push({
        id: this.comments.length + 1,
        text: this.newCommentText,
        author: this.user.username,
        user_id: this.user.id,
      });
      this.showAddCommentForm = false;
      this.newCommentText = "";
    }
  },
};
</script>

<style scoped>
.review-card {
  background-color: #1e1e1e;
  border: 1px solid #333;
  border-radius: 8px;
  padding: 16px;
  color: #e0e0e0;
  margin: 16px;
}

.review-title {
  font-size: 1.2rem;
  margin-bottom: 10px;
  font-weight: bold;
}

.review-text {
  font-size: 1rem;
  margin-bottom: 10px;
}

.review-username {
  font-size: 0.9rem;
  color: #888;
}

.game-title {
  font-size: 1rem;
  margin-bottom: 10px;
  font-weight: bold;
}

.review-actions {
  margin-top: 10px;
}

.btn-secondary,
.btn-danger,
.btn-primary {
  margin-right: 10px;
  color: #fff;
}

.edit-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.review-form-content {
  background: #fff;
  color: #000;
  padding: 20px;
  border-radius: 8px;
  width: 80%;
  max-width: 600px;
  text-align: left;
}

.review-form-content input,
.review-form-content textarea {
  width: 100%;
  margin-bottom: 10px;
}

.review-title-input {
  margin-bottom: 10px;
}

.review-form-content h3 {
  margin-top: 0;
}

input,
textarea {
  display: block;
  width: 100%;
  margin-bottom: 10px;
}

.comment-section {
  margin-top: 20px;
}

.comments-container {
  background-color: #2a2a2a;
  padding: 10px;
  border-radius: 8px;
  margin-top: 10px;
}

.comment {
  border-bottom: 1px solid #444;
  padding: 10px 0;
}

.comment:last-child {
  border-bottom: none;
}

.comment-text {
  font-size: 0.9rem;
  margin-bottom: 5px;
}

.comment-author {
  font-size: 0.8rem;
  color: #888;
}

.comment-actions {
  margin-top: 5px;
}

.add-comment-section {
  margin-top: 10px;
}

.add-comment-form {
  margin-top: 10px;
}
</style>
