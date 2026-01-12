import { useState, useEffect } from 'react'
import { useParams, Link, useNavigate } from 'react-router-dom'

function UserDetail() {
  const { id } = useParams()
  const navigate = useNavigate()
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    fetchUser()
  }, [id])

  const fetchUser = async () => {
    try {
      setLoading(true)
      const response = await fetch(`/api/v1/users/${id}`)
      if (!response.ok) {
        if (response.status === 404) {
          throw new Error('ユーザーが見つかりませんでした')
        }
        throw new Error('ユーザー情報の取得に失敗しました')
      }
      const data = await response.json()
      setUser(data)
      setError(null)
    } catch (err) {
      setError(err.message)
      setUser(null)
    } finally {
      setLoading(false)
    }
  }

  if (loading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="text-gray-600">読み込み中...</div>
      </div>
    )
  }

  if (error) {
    return (
      <div>
        <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded mb-4">
          {error}
        </div>
        <Link
          to="/users"
          className="text-blue-600 hover:text-blue-900 underline"
        >
          ユーザーリストに戻る
        </Link>
      </div>
    )
  }

  if (!user) {
    return null
  }

  return (
    <div>
      <div className="mb-4">
        <button
          onClick={() => navigate(-1)}
          className="text-blue-600 hover:text-blue-900 underline"
        >
          ← 戻る
        </button>
      </div>

      <h1 className="text-3xl font-bold text-gray-900 mb-6">ユーザー詳細</h1>

      <div className="bg-white rounded-lg shadow p-6">
        <dl className="grid grid-cols-1 gap-6 sm:grid-cols-2">
          <div>
            <dt className="text-sm font-medium text-gray-500">ユーザーID</dt>
            <dd className="mt-1 text-sm text-gray-900">{user.id}</dd>
          </div>
          <div>
            <dt className="text-sm font-medium text-gray-500">ユーザー名</dt>
            <dd className="mt-1 text-sm text-gray-900">{user.userName}</dd>
          </div>
          <div>
            <dt className="text-sm font-medium text-gray-500">メールアドレス</dt>
            <dd className="mt-1 text-sm text-gray-900">{user.email}</dd>
          </div>
          <div>
            <dt className="text-sm font-medium text-gray-500">電話番号</dt>
            <dd className="mt-1 text-sm text-gray-900">
              {user.phoneNumber || '-'}
            </dd>
          </div>
          <div>
            <dt className="text-sm font-medium text-gray-500">作成日時</dt>
            <dd className="mt-1 text-sm text-gray-900">
              {user.createdAt
                ? new Date(user.createdAt).toLocaleString('ja-JP')
                : '-'}
            </dd>
          </div>
          <div>
            <dt className="text-sm font-medium text-gray-500">更新日時</dt>
            <dd className="mt-1 text-sm text-gray-900">
              {user.updatedAt
                ? new Date(user.updatedAt).toLocaleString('ja-JP')
                : '-'}
            </dd>
          </div>
        </dl>
      </div>
    </div>
  )
}

export default UserDetail
