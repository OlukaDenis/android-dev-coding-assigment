package com.data.remote.impl

import com.data.remote.remoteMappers.RemoteCommentMapper
import com.data.remote.remoteMappers.RemotePostMapper
import com.data.remote.remoteMappers.RemoteUserMapper
import com.data.remote.services.ApiService
import com.data.utils.BaseRemoteTest
import com.data.utils.apiServiceTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.net.HttpURLConnection

class RemoteRepositoryImplTest : BaseRemoteTest() {

   private lateinit var service: ApiService
   private lateinit var repository: RemoteRepositoryImpl
   private lateinit var remotePostMapper: RemotePostMapper
   private lateinit var remoteUserMapper: RemoteUserMapper
   private lateinit var remoteCommentMapper: RemoteCommentMapper

   @Before
   fun init() {
      super.setUp()
      service = apiServiceTest(getMockWebServerUrl())
      remoteCommentMapper = RemoteCommentMapper()
      remoteUserMapper = RemoteUserMapper()
      remotePostMapper = RemotePostMapper()
      repository = RemoteRepositoryImpl(service, remoteUserMapper, remotePostMapper, remoteCommentMapper)
   }

   @Test
   fun `Create remote user success`() = runBlocking {
      // Given
      mockNetworkResponseWithFileContent(
         "users/single_user.json",
         HttpURLConnection.HTTP_OK
      )

      // When
      val request = hashMapOf<String, Any>()
      val result = repository.createUser(request)

      // Then
      assertThat(result).isNotNull()
      assertThat(result.username).isEqualTo("mcdenny")
   }

   @Test
   fun `Create remote user failure`() = runBlocking {
      // Given
      mockNetworkResponseWithCode(HttpURLConnection.HTTP_BAD_REQUEST)

      // When
      val request = hashMapOf<String, Any>()
      val result = runCatching { repository.createUser(request) }

      // Then
      val error = result.exceptionOrNull()
      assertThat(error).isNotNull()
      assertThat(error).isInstanceOf(HttpException::class.java)
      val code = (error as HttpException).code()
      assertThat(code).isEqualTo(400)
   }

   @Test
   fun `Fetch single remote user success`() = runBlocking {
      // Given
      mockNetworkResponseWithFileContent(
         "users/single_user.json",
         HttpURLConnection.HTTP_OK
      )

      // When
      val result = repository.fetchSingleUser(1)

      // Then
      assertThat(result).isNotNull()
      assertThat(result.username).isEqualTo("mcdenny")
   }

   @Test
   fun `Create remote post success`() = runBlocking {
      // Given
      mockNetworkResponseWithFileContent(
         "posts/single_post.json",
         HttpURLConnection.HTTP_OK
      )

      // When
      val request = hashMapOf<String, Any>()
      val result = repository.createPost(request)

      // Then
      assertThat(result).isNotNull()
      assertThat(result.title).isEqualTo("nesciunt iure omnis dolorem tempora et accusantium")
   }

   @Test
   fun `Update remote post success`() = runBlocking {
      // Given
      mockNetworkResponseWithFileContent(
         "posts/single_post.json",
         HttpURLConnection.HTTP_OK
      )

      // When
      val request = hashMapOf<String, Any>()
      val result = repository.updatePost(1, request)

      // Then
      assertThat(result).isNotNull()
      assertThat(result.title).isEqualTo("nesciunt iure omnis dolorem tempora et accusantium")
   }

   @Test
   fun `Delete remote post success`() = runBlocking {
      // Given
      mockNetworkResponseWithCode(HttpURLConnection.HTTP_OK)

      // When
      val result = runCatching {  repository.deletePost(1) }

      // Then
      assertThat(result.isSuccess).isTrue()
   }

   @Test
   fun `Fetch remote post list success`() = runBlocking {
      // Given
      mockNetworkResponseWithFileContent(
         "posts/post_list.json",
         HttpURLConnection.HTTP_OK
      )

      // When
      val result = repository.fetchPosts()

      // Then
      assertThat(result).isNotNull()
      assertThat(result).isNotEmpty()
   }

   @Test
   fun `Create remote comment success`() = runBlocking {
      // Given
      mockNetworkResponseWithFileContent(
         "comments/single_comment.json",
         HttpURLConnection.HTTP_OK
      )

      // When
      val request = hashMapOf<String, Any>()
      val result = repository.createComment(request)

      // Then
      assertThat(result).isNotNull()
      assertThat(result.email).isEqualTo("mcdenny@me.com")
   }

   @Test
   fun `Fetch remote post comment list success`() = runBlocking {
      // Given
      mockNetworkResponseWithFileContent(
         "comments/comment_list.json",
         HttpURLConnection.HTTP_OK
      )

      // When
      val result = repository.fetchPostComments(1)

      // Then
      assertThat(result).isNotNull()
      assertThat(result).isNotEmpty()
   }

   @Test
   fun `Update remote comment success`() = runBlocking {
      // Given
      mockNetworkResponseWithFileContent(
         "comments/single_comment.json",
         HttpURLConnection.HTTP_OK
      )

      // When
      val request = hashMapOf<String, Any>()
      val result = repository.updateComment(1, request)

      // Then
      assertThat(result).isNotNull()
      assertThat(result.email).isEqualTo("mcdenny@me.com")
   }

   @Test
   fun `Delete remote comment success`() = runBlocking {
      // Given
      mockNetworkResponseWithCode(HttpURLConnection.HTTP_OK)

      // When
      val result = runCatching {  repository.deleteComment(1) }

      // Then
      assertThat(result.isSuccess).isTrue()
   }
}