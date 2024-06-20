package com.kynzai.tictactoe.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.kynzai.tictactoe.R
import com.kynzai.tictactoe.data.PointAction
import com.kynzai.tictactoe.model.Player
import com.kynzai.tictactoe.theme.light_onFlirtContainer
@Composable
fun PlayerItem(
	player: Player, // Игрок
	playerTurn: Boolean, // Индикатор текущего хода игрока
	modifier: Modifier = Modifier // Модификатор для настройки внешнего вида
) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally // Центрирование элементов по горизонтали
	) {
		Card(modifier = modifier) { // Создание карточки с переданным модификатором
			Column(
				horizontalAlignment = Alignment.CenterHorizontally, // Центрирование по горизонтали
				modifier = Modifier
					.padding(16.dp) // Внутренние отступы
			) {
				Text(
					text = player.name, // Имя игрока
					style = MaterialTheme.typography.bodySmall.copy(
						fontWeight = FontWeight.Bold // Жирный шрифт
					)
				)

				Spacer(modifier = Modifier.height(16.dp)) // Отступ между элементами

				PlayerPointType(
					playerTurn = playerTurn, // Передача текущего хода
					pointType = player.pointType // Тип очков игрока
				)
			}
		}

		Spacer(modifier = Modifier.height(8.dp)) // Отступ между элементами

		Card(
			shape = CircleShape, // Форма карточки - круг
			border = BorderStroke(
				width = 1.dp, // Ширина границы
				color = MaterialTheme.colorScheme.onSurfaceVariant // Цвет границы
			)
		) {
			Text(
				text = "Win rounds: ${player.win}", // Текст с количеством выигранных раундов
				style = MaterialTheme.typography.labelMedium.copy(
					fontWeight = FontWeight.Medium, // Средняя толщина шрифта
					color = MaterialTheme.colorScheme.tertiary // Цвет текста
				),
				modifier = Modifier
					.padding(
						horizontal = 8.dp, // Горизонтальные отступы
						vertical = 4.dp // Вертикальные отступы
					)
			)
		}
	}
}

@Composable
private fun PlayerPointType(
	playerTurn: Boolean, // Индикатор текущего хода игрока
	pointType: PointAction // Тип очков игрока
) {
	val pointTypeBackground by animateColorAsState(
		targetValue = if (playerTurn) light_onFlirtContainer // Цвет фона, если ход игрока
		else MaterialTheme.colorScheme.background, // Цвет фона, если не ход игрока
		animationSpec = tween(500), label = "" // Анимация с длительностью 500 мс
	)

	Box(
		modifier = Modifier
			.clip(MaterialTheme.shapes.large) // Обрезка формы
			.drawBehind {
				drawRoundRect(
					color = pointTypeBackground, // Цвет фона
					cornerRadius = CornerRadius(
						x = CircleShape.topStart.toPx(size, this), // Радиус верхнего левого угла
						y = CircleShape.bottomEnd.toPx(size, this) // Радиус нижнего правого угла
					)
				)
			}
	) {
		PlayerPointTypeImage(pointType = pointType) // Вызов функции для отображения иконки типа очков
	}
}

@Composable
private fun PlayerPointTypeImage(
	pointType: PointAction // Тип очков игрока
){
	val icon: ImageVector = when (pointType){
		PointAction.Empty -> Icons.Default.Favorite // Иконка для пустого типа
		PointAction.X -> Icons.Default.Close // Иконка для типа X
		PointAction.O -> ImageVector.vectorResource(id = R.drawable.ic_tic_tac_toe_o) // Иконка для типа O
	}
	Icon(
		imageVector = icon, // Векторное изображение иконки
		contentDescription = null, // Описание контента (необязательно)
		modifier = Modifier
			.padding(horizontal = 12.dp, vertical = 4.dp) // Отступы
			.size(24.dp) // Размер иконки
	)
}

@Composable
fun RoundItem(
	draw: Int, // Количество ничьих
	round: Int, // Номер текущего раунда
	modifier: Modifier = Modifier // Модификатор для настройки внешнего вида
) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally, // Центрирование по горизонтали
		modifier = modifier // Применение переданного модификатора
	) {
		Card(
			shape = RoundedCornerShape(100), // Карточка с закругленными углами
			colors = CardDefaults.cardColors(
				containerColor = MaterialTheme.colorScheme.tertiaryContainer // Цвет контейнера
			)
		) {
			Column(
				horizontalAlignment = Alignment.CenterHorizontally, // Центрирование по горизонтали
				verticalArrangement = Arrangement.Center, // Центрирование по вертикали
				modifier = Modifier
					.padding(8.dp) // Внутренние отступы
					.size(56.dp) // Размер элемента
			) {
				Text(
					text = buildAnnotatedString {
						withStyle(
							MaterialTheme.typography.titleMedium.copy(
								fontWeight = FontWeight.ExtraBold // Очень жирный шрифт
							).toSpanStyle()
						) {
							append("$round") // Добавление текста номера раунда
						}

						withStyle(
							MaterialTheme.typography.bodyMedium.copy(
								fontWeight = FontWeight.ExtraBold, // Очень жирный шрифт
								baselineShift = BaselineShift.Superscript
							).toSpanStyle()
						) {
							append(
								when (round) {
									1 -> "st"
									2 -> "nd"
									3 -> "rd"
									else -> "th"
								}
							)
						}
					}
				)

				Text(
					text = "Round",
					style = MaterialTheme.typography.labelSmall
				)
			}
		}

		Spacer(modifier = Modifier.height(16.dp))

		Text(
			text = buildAnnotatedString {
				withStyle(
					MaterialTheme.typography.titleSmall.toSpanStyle()
				) {
					append("Draw: ")
				}

				withStyle(
					MaterialTheme.typography.titleSmall.copy(
						color = MaterialTheme.colorScheme.primary
					).toSpanStyle()
				) {
					append("$draw times")
				}
			}
		)
	}
}

