package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.MyApplicationTheme

data class VideoItem(
    val id: String,
    val username: String,
    val description: String,
    val tags: String,
    val likes: String,
    val comments: String,
    val shares: String,
    val saves: String,
    val profilePicUrl: String,
    val coverUrl: String
)

val sampleVideos = listOf(
    VideoItem("1", "@creator_one", "Exploring the city vibes tonight 🌃", "#CityLife #Night #Vibes", "1.2M", "45K", "12K", "3K", "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=200&h=200&fit=crop", "https://images.unsplash.com/photo-1600577916048-804c9191e36c?w=1080&h=1920&fit=crop"),
    VideoItem("2", "@tech_guru", "This new AI feature is insane! 🤯", "#AI #Tech #Innovation", "850K", "12K", "8K", "5K", "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=200&h=200&fit=crop", "https://images.unsplash.com/photo-1550751827-4bd374c3f58b?w=1080&h=1920&fit=crop"),
    VideoItem("3", "@nature_lover", "Peaceful mornings in the mountains 🏔️", "#Nature #Travel #Peace", "2.1M", "88K", "21K", "10K", "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=200&h=200&fit=crop", "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=1080&h=1920&fit=crop"),
    VideoItem("4", "@cyberpunk", "Neon nights and digital dreams. 👾", "#Cyberpunk #Neon #Future", "3.4M", "102K", "45K", "22K", "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=200&h=200&fit=crop", "https://images.unsplash.com/photo-1555680202-c86f0e12f086?w=1080&h=1920&fit=crop")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                TimbuktuApp()
            }
        }
    }
}

@Composable
fun TimbuktuApp() {
    var selectedBottomNav by remember { mutableIntStateOf(0) }
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(
                selectedTab = selectedBottomNav,
                onTabSelected = { selectedBottomNav = it }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(bottom = innerPadding.calculateBottomPadding())) {
            VideoFeed()
            TopNavBar()
        }
    }
}

@Composable
fun VideoFeed() {
    val pagerState = rememberPagerState(pageCount = { sampleVideos.size })
    
    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        VideoPlayerItem(video = sampleVideos[page])
    }
}

@Composable
fun VideoPlayerItem(video: VideoItem) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Video Cover (Mocking video with image)
        AsyncImage(
            model = video.coverUrl,
            contentDescription = "Video Thumbnail",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        
        // Dark gradient overlay for text readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                        startY = 500f
                    )
                )
        )
        
        // Right Action Column
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Profile Picture
            Box(contentAlignment = Alignment.BottomCenter) {
                AsyncImage(
                    model = video.profilePicUrl,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.White, CircleShape),
                    contentScale = ContentScale.Crop
                )
                // Follow badge
                Box(
                    modifier = Modifier
                        .offset(y = 8.dp)
                        .size(20.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Follow",
                        tint = Color.Black,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            ActionIcon(Icons.Outlined.FavoriteBorder, video.likes, "Like")
            ActionIcon(Icons.Outlined.ChatBubbleOutline, video.comments, "Comment")
            ActionIcon(Icons.Outlined.BookmarkBorder, video.saves, "Save")
            ActionIcon(Icons.Outlined.Share, video.shares, "Share")
            
            // Spinning Music Disc mock
            AsyncImage(
                model = video.coverUrl,
                contentDescription = "Audio",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(8.dp, Color.DarkGray, CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        
        // Bottom Text Info
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth(0.75f)
                .padding(start = 16.dp, bottom = 24.dp)
        ) {
            Text(
                text = video.username,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = video.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = video.tags,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary, // Neon green tags
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = "Music",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Original Audio - ${video.username}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ActionIcon(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, contentDesc: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { }) {
        Icon(
            imageVector = icon,
            contentDescription = contentDesc,
            tint = Color.White,
            modifier = Modifier.size(36.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun TopNavBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.LiveTv,
            contentDescription = "Live",
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                text = "Following",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "For You",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )
    }
}

@Composable
fun BottomNavBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = Color.White,
        modifier = Modifier.height(80.dp)
    ) {
        NavigationBarItem(
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.Gray,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) },
            icon = { Icon(Icons.Default.PersonSearch, contentDescription = "Discover") },
            label = { Text("Discover", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.Gray,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )
        
        // Upload Button (Neon Green)
        Box(
            modifier = Modifier
                .padding(top = 12.dp)
                .size(44.dp)
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                .clickable { onTabSelected(2) },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Upload",
                tint = Color.Black,
                modifier = Modifier.size(28.dp)
            )
        }

        NavigationBarItem(
            selected = selectedTab == 3,
            onClick = { onTabSelected(3) },
            icon = { Icon(Icons.Outlined.MailOutline, contentDescription = "Inbox") },
            label = { Text("Inbox", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.Gray,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = selectedTab == 4,
            onClick = { onTabSelected(4) },
            icon = { Icon(Icons.Outlined.Person, contentDescription = "Profile") },
            label = { Text("Profile", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.Gray,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )
    }
}
